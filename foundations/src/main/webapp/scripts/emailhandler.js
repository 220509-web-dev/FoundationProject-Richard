window.onload = (() => {
    document.getElementById('send').addEventListener('click', sendEmail)
});

function sendEmail() {
    let email = document.getElementById('email').value;
    let errorContainer = document.getElementById('error-message');
    if (!email) return setError('An email is required.', errorContainer);
    if (email) errorContainer.setAttribute('hidden', true);

    fetch('/soulnotes/email', {
        method:'POST',
        body:JSON.stringify(email)
    }).then(() => {
        let successMsgContainer = document.createElement('p');
        successMsgContainer.setAttribute('class', 'alert alert-success');
        successMsgContainer.innerText = 'Thank you! If your email matches a user, you\'ll get an email to reset your password.';
        document.getElementById('forgotPass').appendChild(successMsgContainer);
        setTimeout(() => {
            window.location.href = '/soulnotes/login.html';
        }, 3000);
    });
}
function setError(message, o) {
    o.removeAttribute('hidden');
    o.innerText = message;
}