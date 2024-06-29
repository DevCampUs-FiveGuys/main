function search() {
    let courseName = $("#courseName").val();
    let courseNum = $("#courseNum").val();
    let roles = $("#roles").val();
    console.log(courseName, courseNum, roles);

    $.ajax({
        type: "get",
        url: "/admin/mypage/list/nums",
        data: {name: courseName, num: courseNum, roles: roles},
        dataType: "json",
        success: function (response) {
            let MemberListTbody = $("#MemberList");
            console.log(response);
            MemberListTbody.empty();

            if (response.length > 0) {
                response.forEach(function (item) {
                    let memberHTML =
                        `<tr>
                        <td>${item.course_name}</td>
                        <td>${item.course_num}</td>
                        <td>${item.name}</td>
                        <td>${item.gender === 0 ? '남자' : '여자'}</td>
                        <td>${item.birth.substring(0, 10)}</td>
                        <td>
                            <select class="roleSelect">
                              <option value="ROLE_GUEST" ${item.roles == 'ROLE_GUEST' ? 'selected' : ''}>게스트</option>
                              <option value="ROLE_TEACHER" ${item.roles == 'ROLE_TEACHER' ? 'selected' : ''}>강사</option>
                              <option value="ROLE_STUDENT" ${item.roles == 'ROLE_STUDENT' ? 'selected' : ''}>수강생</option>
                            </select>
                        </td>
                        <td>
                            <form action="/admin/mypage/updateroles" method="post">
                                <input type="hidden" name="member_id" value="${item.member_id}"/>
                                <input type="hidden" name="roles" class="selectedRoles" value="${item.roles}"/>
                                <button class="approvebtn" type="submit">승인</button>
                            </form>
                        </td>
                        <td>
                            <form action="/admin/mypage/roledeny" method="post">
                                <input type="hidden" name="member_id" value="${item.member_id}"/>
                                <button class="approvebtn" type="submit">거부</button>
                            </form>
                        </td>
                        </tr>
                        `;

                    MemberListTbody.append(memberHTML);
                });
            } else {
                MemberListTbody.append(`<p> 등록된 멤버가 없습니다 </p>`);
            }
        },
        error: function (xhr, status, error) {
            console.error("ajax 에러 : ", status, error);
            alert("멤버 목록을 불러오는 중 오류가 발생했습니다");
        }
    });
}

// 과정명, 기수 선택
function loadCourseNums() {
    let selectedCourseName = $("#courseName").val();

    $.ajax({
        type: "GET",
        url: "/admin/mypage/list/names",
        data: {name: selectedCourseName},
        dataType: "json",
        success: function (ele) {
            console.log(ele);

            let courseNumSelect = $("#courseNum");
            courseNumSelect.empty();
            courseNumSelect.append('<option value="">기수를 선택해주세요</option>');
            $.each(ele, function (index, item) {
                courseNumSelect.append('<option value="' + item + '">' + item + '</option>');
            });
        },
        error: function (e) {
            console.error("Error fetching course numbers: ", e);
        }
    });
}

// 권한 승인 함수
$(".roleSelect").change(function () {
    alert($(this).val()); // 내가 바꾸려고 선택한 권한
    $(this).parent().next().find(".selectedRoles").val($(this).val());
    // $(this) = roleSelect -> parent = td -> next = td -> class가 SElectedRoles걸 찾고 -> selectedRoles의 value를 this의 Value로 바꿔주기
})

function searchrole() {
    let roles = $("#roles").val();
    $.ajax({
        type: "get",
        url: "/admin/mypage/list/role",
        data: {roles: roles},
        dataType: "json",
        success: function (response) {
            let MemberListTbody = $("#MemberList");
            console.log(response);
            MemberListTbody.empty();

            if (response.length > 0) {
                response.forEach(function (item) {
                    let memberHTML =
                        `<tr>
                        <td>${item.course_name}</td>
                        <td>${item.course_num}</td>
                        <td>${item.name}</td>
                        <td>${item.gender === 0 ? '남자' : '여자'}</td>
                        <td>${item.birth.substring(0, 10)}</td>
                        <td>
                            <select class="roleSelect">
                              <option value="ROLE_GUEST" ${item.roles == 'ROLE_GUEST' ? 'selected' : ''}>게스트</option>
                              <option value="ROLE_TEACHER" ${item.roles == 'ROLE_TEACHER' ? 'selected' : ''}>강사</option>
                              <option value="ROLE_STUDENT" ${item.roles == 'ROLE_STUDENT' ? 'selected' : ''}>수강생</option>
                            </select>
                        </td>
                        <td>
                            <form action="/admin/mypage/updateroles" method="post">
                                <input type="hidden" name="member_id" value="${item.member_id}"/>
                                <input type="hidden" name="roles" class="selectedRoles" value="${item.roles}"/>
                                <button class="approvebtn" type="submit">승인</button>
                            </form>
                        </td>
                        <td>
                            <form action="/admin/mypage/roledeny" method="post">
                                <input type="hidden" name="member_id" value="${item.member_id}"/>
                                <button class="approvebtn" type="submit">거부</button>
                            </form>
                        </td>
                        </tr>
                        `;
                    MemberListTbody.append(memberHTML);
                });
            } else {
                MemberListTbody.append(`<p> 등록된 멤버가 없습니다 </p>`);
            }
        },
        error: function (xhr, status, error) {
            console.error("ajax 에러 : ", status, error);
            alert("멤버 목록을 불러오는 중 오류가 발생했습니다");
        }
    });
}

function searchAttendance() {
    let courseName = $("#courseName").val();
    let courseNum = $("#courseNum").val();
    let dateStr = $("#calendardate").val();

    console.log(courseName, courseNum, dateStr);

    $.ajax({
        type: "get",
        url: "/admin/mypage/list/nums/attendance",
        data: {name: courseName, num: courseNum},
        dataType: "json",
        success: function (response) {
            let AttendanceListTbody = $("#AttendanceList");
            console.log(response);
            AttendanceListTbody.empty();

            if (response.length > 0) {
                response.forEach(function (item) {
                    let memberHTML =
                        `<tr>
                        <td>${item.membername}</td>
                        <td>${item.check_in}</td>
                        <td>${item.check_out}</td>
                        <td>${item.late}</td>
                        <td>${item.absent}</td>
                        <td>${item.vacation}</td>
                        <td>${item.hospital}</td>
                        </tr>
                        `;

                    AttendanceListTbody.append(memberHTML);
                });
            } else {
                AttendanceListTbody.append(`<p> 등록된 멤버가 없습니다 </p>`);
            }
        },
        error: function (xhr, status, error) {
            console.error("ajax 에러 : ", status, error);
            alert("멤버 목록을 불러오는 중 오류가 발생했습니다");
        }
    });
}

// 달력 불러오기
document.addEventListener('DOMContentLoaded', function() {
    let calendarEl = document.getElementById('calendar');

    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        dateClick: function(info) {
            window.location.href = '/admin/mypage/attendancedetail?date=' + info.dateStr;
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            $.ajax({
                url: '/admin/mypage/attendanceCounts',
                method: 'GET',
                success: function(data) {
                    let events = [];
                    for (let date in data) {
                        if (data.hasOwnProperty(date)) {
                            events.push({
                                title: '출석 인원 : ' + data[date] + '명',
                                start: date
                            });
                        }
                    }
                    successCallback(events);
                },
                error: function() {
                    failureCallback();
                }
            });
        }
    });
    calendar.render();
});

function updateAbsent(){
    confirm("해당 결석을 병가로 출결수정하시겠습니까?");
}

