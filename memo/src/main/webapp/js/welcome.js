window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const generalErrorMessage = document.getElementById('error-message'); 
    const usernameError = document.getElementById('username-error'); 
    const passwordError = document.getElementById('password-error'); 

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
        validateField(passwordInput, passwordError, 'パスワードを入力してください。');
        clearError(generalErrorMessage); 
    });

    form.addEventListener('submit', function (e) {
        let isValid = true;
        clearError(generalErrorMessage); 
        const isUsernameValid = validateField(usernameInput, usernameError, 'ユーザー名を入力してください。');
        const isPasswordValid = validateField(passwordInput, passwordError, 'パスワードを入力してください。');

        if (!isUsernameValid) {
            usernameInput.focus();
            isValid = false;
        } else if (!isPasswordValid) {
            passwordInput.focus();
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault(); 
            displayError(generalErrorMessage, '入力内容を確認してください。');
        } 
    });
});