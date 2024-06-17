document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth'
    });
    calendar.render();

    document.getElementById('check-in-btn').addEventListener('click', function () {
        alert('입실 시간: ' + new Date().toLocaleString());
    });

    document.getElementById('check-out-btn').addEventListener('click', function () {
        alert('퇴실 시간: ' + new Date().toLocaleString());
    });

    document.getElementById('vacation-form').addEventListener('submit', function (event) {
        event.preventDefault();
        var date = document.getElementById('vacation-date').value;
        var reason = document.getElementById('vacation-reason').value;
        alert('휴가 신청: 날짜: ' + date + ', 사유: ' + reason);
        $('#vacationModal').modal('hide');
    });
});
