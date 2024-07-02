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


// 출석현황 : 날짜 과정명 기수 검색해서 나오는 명단
function searchAttendance() {
    let courseName = $("#courseName").val();
    let courseNum = $("#courseNum").val();
    let dateStr = $("#calendardate").val();

    console.log(courseName, courseNum, dateStr);

    // 캘린더에서 날짜 클릭 후 나오는 멤버 명단을 불러오는 ajax
    $.ajax({
        type: "get",
        url: "/admin/mypage/list/nums/attendance",
        data: {name: courseName, num: courseNum, dateStr: dateStr},
        dataType: "json",
        success: function (response) {
            let AttendanceListTbody = $("#AttendanceList");
            console.log(response);
            AttendanceListTbody.empty();

            if (response.length > 0) {
                response.forEach(function (item) {

                    let checkInTime = formatDate(item.check_in);
                    let checkOutTime = formatDate(item.check_out);

                    let memberHTML =
                        `<tr>
                        <td>${item.membername}</td>
                        <td>${checkInTime}</td>
                        <td>${checkOutTime}</td>
                        <td>${item.late}</td>
                        <td>${item.vacation}</td>
                        <td class="btn-absent">
                        <button class="absent-btn">${item.absent}</button>
                        </td>
                        <td>${item.hospital}</td>
                        <td><button class="hospital-btn">승인</button></td>
                        <td style="display: none">${item.member_id}</td>
                        </tr>
                        `;

                    AttendanceListTbody.append(memberHTML);

                    // 병가 승인 (hospital-btn ) 을 눌렀을 때 나오는 modal
                    $(document).on('click', '.hospital-btn', function() {
                        let membername = $(this).closest('tr').find('td:first').text(); // 해당 row의 첫 번째 열(membername) 가져오기
                        let dateStr = $("#calendardate").val(); // 캘린더에서 불러온 날짜
                        let absent = $(this).closest('tr').find('td:nth-child(6)').text();
                        let member_id = $(this).closest('tr').find('td:last').text();

                        // 해당 날짜의 결석이 1일때마만 병가로 업데이트 해줘야 함
                        if(absent == 1) {
                            Swal.fire({
                                title: '병가 수정',
                                text: membername+ '님의 결석을 병가처리하시겠습니까?',
                                icon: 'info',
                                showCancelButton: true,
                                confirmButtonText: '확인',
                                cancelButtonText: '취소',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    $.ajax({
                                        type: 'GET',
                                        url: '/admin/mypage/attendancedetail/update',
                                        data: { member_id: member_id, dateStr: dateStr },
                                        success: function(response) {
                                            // 병가 처리 성공 시
                                            Swal.fire('처리되었습니다!', '', 'success');
                                            document.location.reload();
                                        },
                                        error: function(xhr, status, error) {
                                            // 오류 처리
                                            console.error('AJAX 오류:', status, error);
                                            Swal.fire('오류가 발생했습니다.', '다시 시도해 주세요.', 'error');
                                        }
                                    });
                                }
                            });
                        } else {
                            Swal.fire({
                                title: '병가승인을 처리할 수 없습니다.',
                                content : '출결을 다시 확인해주세요',
                                icon : 'error',
                                showCancelButton: false,
                            });
                        }
                    });
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

// 출석명단 자세히 보기에서 입퇴실 시간 출력 형태 yyyy-MM-dd HH:mm 형식으로 출력하도록 format하는 함수
function formatDate(dateTimeString) {
    if(!dateTimeString) return'';
    let date = new Date(dateTimeString);

    let formatDate = `${date.getFullYear()}-${('0' + (date.getMonth()+1)).slice(-2)}-${('0' + date.getDate()).slice(-2)}`;
    let formatTime = `${('0' + date.getHours()).slice(-2)}:${('0' + date.getMinutes()).slice(-2)}`;

    return `${formatDate} ${formatTime}`;
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
