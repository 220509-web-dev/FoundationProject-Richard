window.onload = (() => {
    const params = new URLSearchParams(window.location.search);
    fetch('/soulnotes/reset', {
        method:'POST' ,
        body: JSON.stringify(params.get('token'))
    }).then(resp => {
        if (resp.status == 409) {
            window.location.assign("/soulnotes")
        }
    });
    document.getElementById('update').addEventListener('click', update);
    document.getElementById('confirmPass').addEventListener('keyup', e => {
        if (e.key === 'Enter') update();
    });
});

function update() {
    const token = new URLSearchParams(window.location.search).get('token');
    let password = document.getElementById('password').value;
    let confirmPass = document.getElementById('confirmPass').value;
    let errorContainer = document.getElementById('error-message');

    if (!password || !confirmPass) return setError('Please enter all required fields.', errorContainer);
    if (password && confirmPass) errorContainer.setAttribute('hidden', true);

    if (password.length < 8) return setError('Password must be at least eight characters long.', errorContainer);
    if (password != confirmPass) return setError('Passwords must match.', errorContainer);
    fetch('/soulnotes/reset', {
        method: 'PUT',
        body: JSON.stringify({
            token,
            password
        })
    }).then(resp => {
        if (resp.status == 204) {
            let successMsgContainer = document.createElement('p');
            successMsgContainer.setAttribute('class', 'alert alert-success');
            successMsgContainer.innerText = 'Update successful! Redirecting to login page...';
            document.getElementById('reset').appendChild(successMsgContainer);

            setTimeout(() => {
                 window.location.href = '/soulnotes/login.html';
             }, 3000);
        }
    })
}
function setError(message, o) {
    o.removeAttribute('hidden');
    o.innerText = message;
}