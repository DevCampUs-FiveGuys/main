document.addEventListener('DOMContentLoaded', function () {
    // HTML 에서 사용자 ID를 가져와서 변수에 저장
    var memberId = document.getElementById('member-id').getAttribute('data-member-id');

    // FullCalendar 라이브러리를 사용하여 캘린더 생성
    var calendarEl = document.getElementById('calendar'); // 캘린더를 표시할 엘리먼트
    var calendar = new FullCalendar.Calendar(calendarEl, { // 캘린더 객체 생성
        initialView: 'dayGridMonth', // 월 단위로 표시
        events: [], // 이벤트 데이터의 자리 표시자
        eventOrder: 'statusOrder,start,-title', // 사용자 지정 이벤트 순서
        eventClick: function (info) { // 이벤트 클릭 시 동작 정의
            if (info.event.title.startsWith('휴가')) { // 휴가 이벤트일 경우
                Swal.fire({
                    title: '휴가 취소',
                    text: '해당 날짜의 휴가를 취소 하시겠습니까?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: '확인',
                    cancelButtonText: '취소',
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                }).then((result) => {
                    if (result.isConfirmed) { // 확인 버튼 클릭 시
                        info.event.remove(); // 이벤트 삭제
                        deleteVacation(info.event.startStr); // 휴가 데이터 삭제 AJAX 호출
                        Swal.fire({
                            title: '취소 완료',
                            text: '휴가가 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                    }
                });
            } else if (info.event.title.startsWith('입실')) { // 입실 이벤트일 경우
                var checkOutEvent = calendar.getEvents().find(event => event.title.startsWith('퇴실') && event.startStr === info.event.startStr);
                if (checkOutEvent) { // 이미 퇴실이 완료된 경우
                    Swal.fire({
                        title: '취소 오류',
                        text: '이미 퇴실을 완료하여 입실을 취소할 수 없습니다.',
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                    });
                    return;
                }
                Swal.fire({
                    title: '입실 취소',
                    text: '입실을 취소 하시겠습니까?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: '확인',
                    cancelButtonText: '취소',
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                }).then((result) => {
                    if (result.isConfirmed) { // 확인 버튼 클릭 시
                        info.event.remove(); // 이벤트 삭제
                        deleteCheckIn(); // 입실 데이터 삭제 AJAX 호출
                        Swal.fire({
                            title: '취소 완료',
                            text: '입실이 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        checkInDone = false;
                        checkInTime = null;
                    }
                });
            }
        }
    });

    calendar.render(); // 캘린더 렌더링

    loadAttendanceData(); // 출결 데이터 로드

    function formatDate(date) { // 날짜 형식을 'YYYY-MM-DD'로 포맷하는 함수
        return date.getFullYear() + '-' + String(date.getMonth() + 1).padStart(2, '0') + '-' + String(date.getDate()).padStart(2, '0');
    }

    function addEvent(title, date, color, statusOrder) { // 이벤트를 캘린더에 추가하는 함수
        calendar.addEvent({
            title: title,
            start: date,
            allDay: true,
            backgroundColor: color,
            borderColor: color,
            statusOrder: statusOrder
        });
    }

    function removeAttendanceMarks(date) { // 출석, 지각, 결석 마크를 제거하는 함수
        calendar.getEvents().forEach(event => {
            if (event.startStr === date && (event.title === '출석' || event.title === '지각' || event.title === '결석')) {
                event.remove();
            }
        });
    }

    var checkInDone = false;
    var checkOutDone = false;
    var checkInTime = null;
    var checkOutTime = null;

    function resetCheckStatus() { // 입실 및 퇴실 상태 초기화
        checkInDone = false;
        checkOutDone = false;
        checkInTime = null;
        checkOutTime = null;
    }

    function loadAttendanceData() { // 출결 데이터를 로드하는 함수
        $.ajax({
            url: '/student/mypage/attendance/all',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (data) {
                data.forEach(function (attendance) {
                    var date = formatDate(new Date(attendance.check_in || attendance.check_out));

                    if (attendance.check_in) { // 입실 데이터가 있을 경우
                        addEvent('입실: ' + new Date(attendance.check_in).toLocaleTimeString(), date, '#4287f5', 2);
                        checkInDone = true;
                        checkInTime = new Date(attendance.check_in);

                        var checkInHour = checkInTime.getHours();
                        var checkInMinute = checkInTime.getMinutes();

                        if (checkInHour > 9 || (checkInHour === 9 && checkInMinute > 40)) { // 지각 조건
                            status = '지각';
                            statusColor = '#fab70f';
                            if (checkInHour >= 14 || checkOutTime.getHours() < 18 || checkOutTime.getHours() == null) { // 결석 조건
                                status = '결석';
                                statusColor = '#e62e2e';
                            }
                            addEvent(status, date, statusColor, 1);
                        }

                    }
                    if (attendance.check_out) { // 퇴실 데이터가 있을 경우
                        addEvent('퇴실: ' + new Date(attendance.check_out).toLocaleTimeString(), date, '#4287f5', 2);
                        checkOutDone = true;
                        checkOutTime = new Date(attendance.check_out);

                        var status = '출석';
                        var statusColor = '#1dd174';
                        var checkInHour = checkInTime.getHours();
                        var checkInMinute = checkInTime.getMinutes();

                        console.log(checkInHour);

                        if (checkOutTime.getHours() - checkInHour >= 9) {
                            addEvent(status, date, statusColor, 1);
                        }
                    }
                });
                loadVacationData(); // 휴가 데이터 로드
            },
            error: function (xhr, status, error) {
                console.error('Error loading attendance data:', error);
            }
        });
    }

    function loadVacationData() { // 휴가 데이터를 로드하는 함수
        $.ajax({
            url: '/student/mypage/vacation/all',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (data) {
                data.forEach(function (vacation) {
                    var date = formatDate(new Date(vacation.date));
                    addEvent('휴가', date, '#934bd6', 1);
                });
                updateAttendanceCounts(); // 모든 데이터 로드 후 카운트 업데이트
            },
            error: function (xhr, status, error) {
                console.error('Error loading vacation data:', error);
            }
        });
    }

    document.getElementById('check-in-btn').addEventListener('click', function () { // 입실 버튼 클릭 시 동작
        var currentDate = formatDate(new Date());

        if (checkInDone && checkInTime && formatDate(checkInTime) === currentDate) { // 이미 입실한 경우
            Swal.fire({
                title: '입실 오류',
                text: '오늘 이미 입실했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        var currentTime = new Date();
        checkInTime = currentTime;

        Swal.fire({
            title: '입실 확인',
            text: '현재 시각은 ' + currentTime.toLocaleString() + ' 입니다. 입실 하시겠습니까?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
        }).then((result) => {
            if (result.isConfirmed) { // 확인 버튼 클릭 시
                $.ajax({
                    url: '/student/mypage/attendance/checkin',
                    method: 'POST',
                    data: {
                        check_in: currentDate,
                        member_id: memberId
                    },
                    success: function () {
                        Swal.fire({
                            title: '입실 완료',
                            text: '입실 시간: ' + checkInTime.toLocaleTimeString(),
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        addEvent('입실: ' + checkInTime.toLocaleTimeString(), currentDate, '#4287f5', 2);
                        checkInDone = true;
                    },
                    error: function (xhr, status, error) {
                        console.error('Error checking in:', error);
                    }
                });
            }
        });
    });

    document.getElementById('check-out-btn').addEventListener('click', function () { // 퇴실 버튼 클릭 시 동작
        var currentDate = formatDate(new Date());

        if (!checkInDone) { // 입실하지 않은 경우
            Swal.fire({
                title: '퇴실 오류',
                text: '아직 입실하지 않았습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }
        if (checkOutDone && checkOutTime && formatDate(checkOutTime) === currentDate) { // 이미 퇴실한 경우
            Swal.fire({
                title: '퇴실 오류',
                text: '오늘 이미 퇴실했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        var currentTime = new Date();
        checkOutTime = currentTime;

        Swal.fire({
            title: '퇴실 확인',
            text: '현재 시각은 ' + currentTime.toLocaleString() + ' 입니다. 퇴실 하시겠습니까?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
        }).then((result) => {
            if (result.isConfirmed) { // 확인 버튼 클릭 시
                $.ajax({
                    url: '/student/mypage/attendance/checkout',
                    method: 'POST',
                    data: {
                        check_out: currentDate,
                        member_id: memberId
                    },
                    success: function () {
                        Swal.fire({
                            title: '퇴실 완료',
                            text: '퇴실 시간: ' + checkOutTime.toLocaleTimeString(),
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        addEvent('퇴실: ' + checkOutTime.toLocaleTimeString(), currentDate, '#4287f5', 2);
                        checkOutDone = true;

                        var status = '출석';
                        var statusColor = '#1dd174';
                        var checkInHour = checkInTime.getHours();
                        var checkInMinute = checkInTime.getMinutes();

                        if (checkInHour > 9 || (checkInHour == 9 && checkInMinute > 40)) { // 지각 조건
                            status = '지각';
                            statusColor = '#fab70f';
                            if (checkInHour >= 14 || currentTime.getHours() < 18) { // 결석 조건
                                status = '결석';
                                statusColor = '#e62e2e';
                            }
                        }

                        addEvent(status, currentDate, statusColor, 1);
                        updateAttendanceStatus(status); // 출결 상태 업데이트
                    },
                    error: function (xhr, status, error) {
                        console.error('Error checking out:', error);
                    }
                });
            }
        });
    });

    function deleteCheckIn() { // 입실 데이터를 삭제하는 함수
        $.ajax({
            url: '/student/mypage/attendance/checkin',
            method: 'DELETE',
            data: {
                member_id: memberId
            },
            success: function () {
                console.log('Check-in data deleted');
            },
            error: function (xhr, status, error) {
                console.error('Error deleting check-in data:', error);
            }
        });
    }

    function deleteVacation(date) { // 휴가 데이터를 삭제하는 함수
        $.ajax({
            url: '/student/mypage/vacation/cancel',
            method: 'DELETE',
            data: {
                member_id: memberId,
                date: date
            },
            success: function () {
                console.log('Vacation data deleted for date:', date);
            },
            error: function (xhr, status, error) {
                console.error('Error deleting vacation data:', error);
            }
        });
    }

    document.getElementById('vacation-form').addEventListener('submit', function (event) { // 휴가 신청 폼 제출 시 동작
        event.preventDefault();
        var date = document.getElementById('vacation-date').value;
        var reason = document.getElementById('vacation-reason').value;
        var currentDate = new Date();
        var formattedCurrentDate = formatDate(currentDate);

        if (date <= formattedCurrentDate) { // 과거 또는 현재 날짜에 휴가 신청 불가
            Swal.fire({
                title: '휴가 신청 오류',
                text: '오늘 날짜 및 지난 날짜에는 휴가를 신청할 수 없습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        var courseStartDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 6, currentDate.getDate());
        var formattedCourseStartDate = formatDate(courseStartDate);

        var existingVacation = calendar.getEvents().some(event => event.startStr === date && event.title === '휴가');
        var vacationsLastSixMonths = calendar.getEvents().filter(event => event.title === '휴가' && event.startStr >= formattedCourseStartDate).length;

        if (existingVacation) { // 이미 해당 날짜에 휴가가 신청된 경우
            Swal.fire({
                title: '휴가 신청 오류',
                text: '이미 해당 날짜에 휴가를 신청했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }
        if (vacationsLastSixMonths >= 6) { // 휴가 일수가 6일을 초과한 경우
            Swal.fire({
                title: '휴가 신청 오류',
                text: '과정 기간 중 사용하신 휴가일수가 이미 6일을 달성했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        Swal.fire({
            title: '휴가 신청',
            text: date + ' 에 휴가를 신청 하시겠습니까?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
        }).then((result) => {
            if (result.isConfirmed) { // 확인 버튼 클릭 시
                $.ajax({
                    url: '/student/mypage/vacation/apply',
                    method: 'POST',
                    data: {
                        date: date,
                        reason: reason,
                        member_id: memberId
                    },
                    success: function () {
                        Swal.fire({
                            title: '휴가 신청 완료',
                            text: '휴가 날짜: ' + date + '\n휴가 사유: ' + reason,
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        addEvent('휴가', date, '#934bd6', 1);
                        $('#vacationModal').modal('hide');
                        updateAttendanceCounts(); // 출결 카운트 업데이트
                    },
                    error: function (xhr, status, error) {
                        console.error('Error applying for vacation:', error);
                    }
                });
            }
        });
    });

    function finalizeAttendance() { // 출결 상태를 최종 확정하는 함수
        var currentTime = new Date();
        var previousDate = new Date(currentTime);
        previousDate.setDate(previousDate.getDate() - 1);
        var currentDate = formatDate(currentTime);
        var previousFormattedDate = formatDate(previousDate);
        var events = calendar.getEvents();

        var checkInEvent = events.find(event => event.title.startsWith('입실') && event.startStr === previousFormattedDate);
        var checkOutEvent = events.find(event => event.title.startsWith('퇴실') && event.startStr === previousFormattedDate);

        if (!checkInEvent || !checkOutEvent) { // 입실이랑 퇴실 기록 둘 다 없는 경우 결석 처리
            addEvent('결석', previousFormattedDate, '#e62e2e', 1);
            updateAttendanceStatus('결석');
        }

        resetCheckStatus(); // 상태 초기화
    }

    function updateAttendanceStatus(status) { // 출결 상태를 업데이트하는 함수
        if (status === '출석') {
            $.ajax({
                url: '/student/mypage/attendance/days',
                method: 'GET',
                data: {
                    member_id: memberId
                },
                success: function (attendanceDays) {
                    updateAttendanceCounts();
                },
                error: function (xhr, status, error) {
                    console.error('Error updating attendance days:', error);
                }
            });
        } else if (status === '지각') {
            $.ajax({
                url: '/student/mypage/attendance/late',
                method: 'POST',
                data: {
                    member_id: memberId
                },
                success: function () {
                    $.ajax({
                        url: '/student/mypage/attendance/updateAbsentBasedOnLate',
                        method: 'POST',
                        data: {
                            member_id: memberId
                        },
                        success: function () {
                            updateAttendanceCounts();
                        },
                        error: function (xhr, status, error) {
                            console.error('Error updating absent based on late status:', error);
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.error('Error updating late status:', error);
                }
            });
        } else if (status === '결석') {
            $.ajax({
                url: '/student/mypage/attendance/absent',
                method: 'POST',
                data: {
                    member_id: memberId
                },
                success: function () {
                    updateAttendanceCounts();
                },
                error: function (xhr, status, error) {
                    console.error('Error updating absent status:', error);
                }
            });
        }
    }

    function updateAttendanceCounts() { // 출결 카운트를 업데이트하는 함수
        $.ajax({
            url: '/student/mypage/attendance/days',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (attendanceDays) {
                document.getElementById('attendance-count').innerText = attendanceDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching attendance days:', error);
            }
        });

        $.ajax({
            url: '/student/mypage/attendance/late/max',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (lateDays) {
                document.getElementById('late-count').innerText = lateDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching late days:', error);
            }
        });

        $.ajax({
            url: '/student/mypage/attendance/absent/max',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (absentDays) {
                document.getElementById('absent-count').innerText = absentDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching absent days:', error);
            }
        });

        $.ajax({
            url: '/student/mypage/vacation/approved',
            method: 'GET',
            data: {
                member_id: memberId
            },
            success: function (approvedVacations) {
                document.getElementById('vacation-count').innerText = approvedVacations.length;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching approved vacation days:', error);
            }
        });
    }

    setTimeout(function (){
        var now = new Date();

        if (now.getHours() === 0 && now.getMinutes() === 0) { // 자정이 되면 상태 초기화
            finalizeAttendance(); // 자정에 결석 상태 확정
            resetCheckStatus();
        }
    })
});
