let isExist = false;

$(function () {
    $("#btnCheckID").on("click", function () {
        if ($("#email").val() === '') {
            alert("가입할 아이디를 입력하세요.")
            return;
        }
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/api/user/checkID",
            data: {"searchID": $("#email").val()},
            success: function (data) {
                if (data.count === 0) {
                    alert("가입이 가능한 아이디 입니다.")
                    isExist = true;
                } else {
                    alert("중복된 아이디 입니다.")
                    isExist = false;
                    $("#email").val("");
                }
            }
        })
    })
    // id 입력시 다시 중복확인을 누르도록 중복 변수를 초기화 한다.
    $("#email").on("keyup", function () {
        isExist = false;
    })
    // 비밀번호 입력시 조건 추가
    let passwordInput = document.getElementById("password");
    let passwordError = document.getElementById("passwordError");
    let regex = /^(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/;
    $("#password").on("keyup", function () {
        if (regex.test(passwordInput.value)) {
            passwordError.textContent = '';
            passwordInput.setCustomValidity('');
        } else {
            passwordError.textContent = '비밀번호는 특수문자를 포함하여 8자리 이상이어야 합니다.';
            passwordInput.setCustomValidity('Invalid password');
        }
    })

    // 이름 입력시 조건 추가
    let regexName = /[ㄱ-ㅎㅏ-ㅣ!@#$%^&*(),.?":{}|<>]/;
    let nameInput = document.getElementById("name");
    let nameError = document.getElementById("nameError");
    $("#name").on("keyup", function () {
        if (regexName.test(nameInput.value)) {
            nameError.textContent = '제대로 된 이름을 입력하세요';
            nameInput.setCustomValidity('Invalid name');
        } else {
            nameError.textContent = '';
            nameInput.setCustomValidity('');
        }
    })
})

function check() {
    if (!isExist) {
        alert("중복확인을 해주세요");
        return false; // false 반환시 action 실행을 하지 못하게 한다.
    }
}

var eye_icon_login = document.getElementById('eye_icon_login');
var login_password = document.getElementById("password");
eye_icon_login.addEventListener('click', () => {
    hideAndShowPass(eye_icon_login, login_password);
});
const hideAndShowPass = (eye_icon, password) => {
    if (eye_icon.classList.contains("bi-eye-slash-fill")) {
        eye_icon.classList.remove('bi-eye-slash-fill');
        eye_icon.classList.add('bi-eye-fill');
        password.setAttribute('type', 'text');

    } else {
        eye_icon.classList.remove('bi-eye-fill');
        eye_icon.classList.add('bi-eye-slash-fill');
        password.setAttribute('type', 'password');
    }
};
