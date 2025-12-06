// register.js の改善案
window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registerForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('passwordConfirm'); 
    const generalErrorMessage = document.getElementById('error-message'); 
    const usernameError = document.getElementById('register-username-error'); 
    const passwordError = document.getElementById('register-password-error'); 
    const passwordConfirmError = document.getElementById('register-password-confirm-error'); 

    function displayError(element, message) {
        if (element) {
            element.textContent = message;
            element.style.color = 'red';
        }
    }

    function clearError(element) {
        if (element) {
            element.textContent = '';
        }
    }

    function validateField(inputElement, errorElement, errorMessage) {
        if (inputElement.value.trim() === '') {
            displayError(errorElement, errorMessage);
            return false;
        } else {
            clearError(errorElement);
            return true;
        }
    }

    usernameInput.addEventListener('input', () => {
        validateField(usernameInput, usernameError, 'ユーザー名を入力してください。');
        clearError(generalErrorMessage);
    });

    passwordInput.addEventListener('input', () => {
        const complexityError = validatePasswordComplexity(passwordInput.value);
        if (complexityError) {
            displayError(passwordError, complexityError);
        } else {
            validateField(passwordInput, passwordError, 'パスワードを入力してください。');
        }
        checkPasswordMatch();
        clearError(generalErrorMessage);
    });

    passwordConfirmInput.addEventListener('input', () => {
        checkPasswordMatch();
        clearError(generalErrorMessage);
    });

    function checkPasswordMatch() {
        if (passwordInput.value !== passwordConfirmInput.value) {
            displayError(passwordConfirmError, 'パスワードが一致しません。');
            return false;
        } else {
            clearError(passwordConfirmError);
            return true;
        }
    }

    form.addEventListener('submit', function(e) {
        let isValid = true;
        clearError(generalErrorMessage); 

        const isUsernameValid = validateField(usernameInput, usernameError, 'ユーザー名を入力してください。');
        const isPasswordValid = validateField(passwordInput, passwordError, 'パスワードを入力してください。');
        const isPasswordConfirmed = checkPasswordMatch();

        const passwordComplexityError = validatePasswordComplexity(passwordInput.value);
        if (passwordComplexityError) {
            displayError(passwordError, passwordComplexityError);
            isValid = false;
        }

        if (!isUsernameValid) {
            usernameInput.focus();
            isValid = false;
        } else if (!isPasswordValid || passwordComplexityError) {
            passwordInput.focus();
            isValid = false;
        } else if (!isPasswordConfirmed) {
            passwordConfirmInput.focus();
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault(); 
            displayError(generalErrorMessage, '入力内容を確認してください。');
        } 
    });
});