document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: [], // Placeholder for events
        eventOrder: 'statusOrder,start,-title', // Custom event order
        eventClick: function (info) {
            if (info.event.title.startsWith('휴가')) {
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
                    if (result.isConfirmed) {
                        info.event.remove();
                        removeAttendanceMarks(info.event.startStr);
                        deleteVacation(info.event.startStr); // AJAX call to delete vacation
                        Swal.fire({
                            title: '취소 완료',
                            text: '휴가가 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                    }
                });
            } else if (info.event.title.startsWith('입실')) {
                var checkOutEvent = calendar.getEvents().find(event => event.title.startsWith('퇴실') && event.startStr === info.event.startStr);
                if (checkOutEvent) {
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
                    if (result.isConfirmed) {
                        info.event.remove();
                        removeAttendanceMarks(info.event.startStr);
                        deleteCheckIn(); // AJAX call to delete check-in
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

    calendar.render();

    loadAttendanceData();

    function formatDate(date) {
        return date.getFullYear() + '-' + String(date.getMonth() + 1).padStart(2, '0') + '-' + String(date.getDate()).padStart(2, '0');
    }

    function addEvent(title, date, color, statusOrder) {
        calendar.addEvent({
            title: title,
            start: date,
            allDay: true,
            backgroundColor: color,
            borderColor: color,
            statusOrder: statusOrder
        });
    }

    function removeAttendanceMarks(date) {
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

    function resetCheckStatus() {
        checkInDone = false;
        checkOutDone = false;
        checkInTime = null;
        checkOutTime = null;
    }

    function loadAttendanceData() {
        $.ajax({
            url: '/student_mypage/attendance/all',
            method: 'GET',
            data: {
                member_id: 1
            },
            success: function (data) {
                data.forEach(function (attendance) {
                    var date = formatDate(new Date(attendance.check_in || attendance.check_out));

                    if (attendance.check_in) {
                        addEvent('입실: ' + new Date(attendance.check_in).toLocaleTimeString(), date, '#4287f5', 2);
                        checkInDone = true;
                        checkInTime = new Date(attendance.check_in);
                    }
                    if (attendance.check_out) {
                        addEvent('퇴실: ' + new Date(attendance.check_out).toLocaleTimeString(), date, '#4287f5', 2);
                        checkOutDone = true;
                        checkOutTime = new Date(attendance.check_out);

                        var status = '출석';
                        var statusColor = '#1dd174';
                        var checkInHour = checkInTime.getHours();
                        var checkInMinute = checkInTime.getMinutes();

                        if (checkInHour > 9 || (checkInHour == 9 && checkInMinute > 40)) {
                            status = '지각';
                            statusColor = '#fab70f';
                            if (checkInHour >= 14) {
                                status = '결석';
                                statusColor = '#e62e2e';
                            }
                        }
                        addEvent(status, date, statusColor, 1);
                    }
                });
                updateAttendanceCounts();
            },
            error: function (xhr, status, error) {
                console.error('Error loading attendance data:', error);
            }
        });
    }

    document.getElementById('check-in-btn').addEventListener('click', function () {
        if (checkInDone) {
            Swal.fire({
                title: '입실 오류',
                text: '오늘 이미 입실했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        var currentTime = new Date();
        var currentDate = formatDate(currentTime);
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
            if (result.isConfirmed) {
                $.ajax({
                    url: '/student_mypage/attendance/checkin',
                    method: 'POST',
                    data: {
                        check_in: currentDate,
                        member_id: 1
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

    document.getElementById('check-out-btn').addEventListener('click', function () {
        if (!checkInDone) {
            Swal.fire({
                title: '퇴실 오류',
                text: '아직 입실하지 않았습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }
        if (checkOutDone) {
            Swal.fire({
                title: '퇴실 오류',
                text: '오늘 이미 퇴실했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        var currentTime = new Date();
        var currentDate = formatDate(currentTime);
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
            if (result.isConfirmed) {
                $.ajax({
                    url: '/student_mypage/attendance/checkout',
                    method: 'POST',
                    data: {
                        check_out: currentDate,
                        member_id: 1
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

                        if (checkInHour > 9 || (checkInHour == 9 && checkInMinute > 40)) {
                            status = '지각';
                            statusColor = '#fab70f';
                            if (checkInHour >= 14) {
                                status = '결석';
                                statusColor = '#e62e2e';
                            }
                        }

                        addEvent(status, currentDate, statusColor, 1);
                        updateAttendanceStatus(status);
                    },
                    error: function (xhr, status, error) {
                        console.error('Error checking out:', error);
                    }
                });
            }
        });
    });

    function deleteCheckIn() {
        $.ajax({
            url: '/student_mypage/attendance/checkin',
            method: 'DELETE',
            data: {
                member_id: 1
            },
            success: function () {
                console.log('Check-in data deleted');
            },
            error: function (xhr, status, error) {
                console.error('Error deleting check-in data:', error);
            }
        });
    }

    function deleteVacation(date) {
        console.log('Vacation data deleted for date:', date);
    }

    document.getElementById('vacation-form').addEventListener('submit', function (event) {
        event.preventDefault();
        var date = document.getElementById('vacation-date').value;
        var reason = document.getElementById('vacation-reason').value;
        var currentDate = new Date();
        var formattedCurrentDate = formatDate(currentDate);

        if (date <= formattedCurrentDate) {
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

        if (existingVacation) {
            Swal.fire({
                title: '휴가 신청 오류',
                text: '이미 해당 날짜에 휴가를 신청했습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }
        if (vacationsLastSixMonths >= 6) {
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
            if (result.isConfirmed) {
                $.ajax({
                    url: '/student_mypage/attendance/vacation',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        check_in: date,
                        vacation: 1,
                        member_id: 1
                    }),
                    success: function () {
                        Swal.fire({
                            title: '휴가 신청 완료',
                            text: '휴가 날짜: ' + date + '\n휴가 사유: ' + reason,
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        addEvent('휴가', date, '#934bd6', 1);
                        $('#vacationModal').modal('hide');
                    },
                    error: function (xhr, status, error) {
                        console.error('Error applying for vacation:', error);
                    }
                });
            }
        });
    });

    function finalizeAttendance() {
        var currentTime = new Date();
        var currentDate = formatDate(currentTime);
        var events = calendar.getEvents();
        var checkInEvent = events.find(event => event.title.startsWith('입실') && event.startStr === currentDate);
        var checkOutEvent = events.find(event => event.title.startsWith('퇴실') && event.startStr === currentDate);

        if (!checkInEvent && !checkOutEvent) {
            addEvent('결석', currentDate, '#e62e2e', 1);
            updateAttendanceStatus('결석');
        } else if (checkInEvent && !checkOutEvent) {
            addEvent('결석', currentDate, '#e62e2e', 1);
            updateAttendanceStatus('결석');
        }

        resetCheckStatus();
    }

    function updateAttendanceStatus(status) {
        if (status === '출석') {
            $.ajax({
                url: '/student_mypage/attendance/days',
                method: 'GET',
                data: {
                    member_id: 1
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
                url: '/student_mypage/attendance/late',
                method: 'POST',
                data: {
                    member_id: 1
                },
                success: function () {
                    updateAttendanceCounts();
                },
                error: function (xhr, status, error) {
                    console.error('Error updating late status:', error);
                }
            });
        } else if (status === '결석') {
            $.ajax({
                url: '/student_mypage/attendance/absent',
                method: 'POST',
                data: {
                    member_id: 1
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

    function updateAttendanceCounts() {
        $.ajax({
            url: '/student_mypage/attendance/days',
            method: 'GET',
            data: {
                member_id: 1
            },
            success: function (attendanceDays) {
                document.getElementById('attendance-count').innerText = attendanceDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching attendance days:', error);
            }
        });

        $.ajax({
            url: '/student_mypage/attendance/late/max',
            method: 'GET',
            data: {
                member_id: 1
            },
            success: function (lateDays) {
                document.getElementById('late-count').innerText = lateDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching late days:', error);
            }
        });

        $.ajax({
            url: '/student_mypage/attendance/absent/max',
            method: 'GET',
            data: {
                member_id: 1
            },
            success: function (absentDays) {
                document.getElementById('absent-count').innerText = absentDays;
            },
            error: function (xhr, status, error) {
                console.error('Error fetching absent days:', error);
            }
        });
    }

    setInterval(function () {
        var now = new Date();
        if (now.getHours() === 23 && now.getMinutes() === 59) {
            finalizeAttendance();
        }
    }, 60000);
});
