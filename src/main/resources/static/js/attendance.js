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
                        Swal.fire({
                            title: '취소 완료',
                            text: '휴가가 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                    }
                });
            } else if (info.event.title.startsWith('입실')) {
                if (checkOutDone) {
                    Swal.fire({
                        title: '취소 오류',
                        text: '퇴실을 먼저 취소해야 입실을 취소할 수 있습니다.',
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                    });
                    return;
                }
                var currentTime = new Date();
                if ((currentTime - checkInTime) > 300000) { // 5 minutes in milliseconds
                    Swal.fire({
                        title: '취소 오류',
                        text: '입실 후 5분이 지나 취소할 수 없습니다.',
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
            } else if (info.event.title.startsWith('퇴실')) {
                var currentTime = new Date();
                if ((currentTime - checkOutTime) > 300000) { // 5 minutes in milliseconds
                    Swal.fire({
                        title: '취소 오류',
                        text: '퇴실 후 5분이 지나 취소할 수 없습니다.',
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                    });
                    return;
                }
                Swal.fire({
                    title: '퇴실 취소',
                    text: '퇴실을 취소 하시겠습니까?',
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
                        Swal.fire({
                            title: '취소 완료',
                            text: '퇴실이 취소되었습니다.',
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                        });
                        checkOutDone = false;
                        checkOutTime = null;
                    }
                });
            }
        }
    });
    calendar.render();

    // Utility function to format dates
    function formatDate(date) {
        return date.getFullYear() + '-' + String(date.getMonth() + 1).padStart(2, '0') + '-' + String(date.getDate()).padStart(2, '0');
    }

    // Function to add an event to the calendar with color and custom property
    function addEvent(title, date, color, statusOrder) {
        calendar.addEvent({
            title: title,
            start: date,
            allDay: true,
            backgroundColor: color,
            borderColor: color,
            statusOrder: statusOrder // Custom property to control event order
        });
    }

    // Function to remove attendance marks (출석, 지각, 결석) for a given date
    function removeAttendanceMarks(date) {
        calendar.getEvents().forEach(event => {
            if (event.startStr === date && (event.title === '출석' || event.title === '지각' || event.title === '결석')) {
                event.remove();
            }
        });
    }

    // Track check-in and check-out status and times
    var checkInDone = false;
    var checkOutDone = false;
    var checkInTime = null;
    var checkOutTime = null;

    // Reset check-in and check-out status at the end of each day
    function resetCheckStatus() {
        checkInDone = false;
        checkOutDone = false;
        checkInTime = null;
        checkOutTime = null;
    }

    // Check-in button event listener
    document.getElementById('check-in-btn').addEventListener('click', function () {
        if (checkInDone) {
            Swal.fire({
                title: '입실 오류',
                text: '이미 오늘 입실했습니다.',
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
                Swal.fire({
                    title: '입실 완료',
                    text: '입실 시간: ' + checkInTime.toLocaleTimeString(),
                    icon: 'success',
                    confirmButtonColor: '#3085d6',
                });
                addEvent('입실: ' + checkInTime.toLocaleTimeString(), currentDate, '#4287f5', 2);
                checkInDone = true;
            }
        });
    });

    // Check-out button event listener
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
                text: '이미 오늘 퇴실했습니다.',
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
                Swal.fire({
                    title: '퇴실 완료',
                    text: '퇴실 시간: ' + checkOutTime.toLocaleTimeString(),
                    icon: 'success',
                    confirmButtonColor: '#3085d6',
                });
                addEvent('퇴실: ' + checkOutTime.toLocaleTimeString(), currentDate, '#4287f5', 2);
                checkOutDone = true;

                // Determine attendance status
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
            }
        });
    });

    // Vacation form submission event listener
    document.getElementById('vacation-form').addEventListener('submit', function (event) {
        event.preventDefault();
        var date = document.getElementById('vacation-date').value;
        var reason = document.getElementById('vacation-reason').value;
        var currentDate = new Date();
        var formattedCurrentDate = formatDate(currentDate);

        // Prevent vacation requests for today or past dates
        if (date <= formattedCurrentDate) {
            Swal.fire({
                title: '휴가 신청 오류',
                text: '오늘 날짜 및 지난 날짜에는 휴가를 신청할 수 없습니다.',
                icon: 'error',
                confirmButtonColor: '#3085d6',
            });
            return;
        }

        // Calculate the start date of the 6-month period
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
                Swal.fire({
                    title: '휴가 신청 완료',
                    text: '휴가 날짜: ' + date + '\n휴가 사유: ' + reason,
                    icon: 'success',
                    confirmButtonColor: '#3085d6',
                });
                addEvent('휴가', date, '#934bd6', 1);
                $('#vacationModal').modal('hide');
            }
        });
    });

    // Additional logic to determine and update attendance status at the end of the day
    function finalizeAttendance() {
        var currentTime = new Date();
        var currentDate = formatDate(currentTime);
        var events = calendar.getEvents();
        var checkInEvent = events.find(event => event.title.startsWith('입실') && event.startStr === currentDate);
        var checkOutEvent = events.find(event => event.title.startsWith('퇴실') && event.startStr === currentDate);

        if (!checkInEvent && !checkOutEvent) {
            // No check-in and no check-out recorded, mark as absent
            addEvent('결석', currentDate, '#e62e2e', 1);
        } else if (checkInEvent && !checkOutEvent) {
            // Check-in but no check-out recorded, mark as absent
            addEvent('결석', currentDate, '#e62e2e', 1);
        }

        // Reset check-in and check-out status at the end of the day
        resetCheckStatus();
    }

    // Schedule finalizeAttendance to run at the end of each day (23:59)
    setInterval(function () {
        var now = new Date();
        if (now.getHours() === 23 && now.getMinutes() === 59) {
            finalizeAttendance();
        }
    }, 60000); // Check every minute
});
