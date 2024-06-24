// <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
// <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

function loadCourseNums() {
    let selectedCourseName = $("#courseName").val();

    $.ajax({
        type: "GET",
        url: "/review/names",
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

function search() {
    let courseName = $("#courseName").val();
    let courseNum = $("#courseNum").val();
    console.log(courseName, courseNum);

    $.ajax({
        type: "GET",
        url: "/review/nums",
        data: {name: courseName, num: courseNum},
        dataType: "json",
        success: function (response) {
            let reviewListDiv = $("#reviewList");
            console.log(response);
            reviewListDiv.empty(); // Clear the existing reviews

            if (response.length > 0) {
                response.forEach(function (item) {
                    let reviewHTML = `
                        <div class="reviews">
                        <div class="reviews-box">
                            <div class="reviews-box-1">
                                <div class="reviews-box-2">
                                    <div class="review-name">${item.name}</div>
                                    <fieldset class="rate2">
                                        ${generateStars(item.star)}
                                    </fieldset>
                                    <div class="review-gender" th:text="${item.gender === 0 ? '남자' : '여자'}"></div>
                                </div>
                                 <div>
                            <button class="icon-heart" onclick="updateLike()">${item.like}</button>
                        </div>
                            </div>
                            <div class="review-content">${item.content}</div>
                            <div class="reviews-box-3">
                                <div class="review-writetime">${formatDate(item.created_at)}</div>
                                <a href="/review/delete?review_id=${item.review_id}">
                                    <i class="icon-delete"></i>
                                </a>
                            </div>
                        </div>
                        </div>
`;
                    reviewListDiv.append(reviewHTML);
                });
            } else {
                reviewListDiv.append("<p class='non-list'>등록된 후기가 없습니다.</p>");
            }
        },
        error: function (xhr, status, error) {
            console.error('Ajax Error : ', status, error);
            alert('후기 목록을 불러오는 중 오류가 발생했습니다.');
        }
    });
}

function generateStars(starRating) {
    let starsHTML = "";
    const totalStar = 5;
    const starVal = totalStar - starRating;


    for (let i = 1; i <= totalStar; i++) {
        const starVal = totalStar - i + 1;

        if (starVal <= starRating) {
            starsHTML += `<input type="radio" value="${starVal}" checked><label title="${starVal}점"></label>`
        } else if (starVal - 0.5 === starRating) {
            starsHTML += `<input type="radio" value="${starVal}"><label title="${starVal}점"></label>`;
            starsHTML += `<input type="radio" value="${starVal}.5" checked><label class="half" title="${starVal - 0.5}점"></label>`;
        } else {
            // 그 외의 경우 비어 있는 별을 추가하여 표시
            starsHTML += `<input type="radio" value="${starVal}"><label title="${starVal}점"></label>`;
        }
    }
    return starsHTML;
}

function formatDate(dateString) {
    let date = new Date(dateString);
    return date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
}

// 평점 반영 함수
function updateStarValue() {
    let selectedRating = document.querySelector('input[name="rating"]:checked').value;
    if (selectedRating) {
        document.getElementById('star').value = selectedRating;
    }
}

function updateLike() {
    console.log();
}

