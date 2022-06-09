window.onload = (() => {
    document.getElementById("register").addEventListener("click", register);
    document.getElementById("confirmPassword").addEventListener("click", (e) => {
        if (e.key === 'Enter') register();
    });
});

function register() {
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let username = document.getElementById("username").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let confirmPass = document.getElementById("confirmPassword").value;
    let errorContainer = document.getElementById('error-message');

    if (!firstName || !lastName || !username || !email || !password || !confirmPass) return setError('Please enter all required fields.', errorContainer);

    if (lastName && lastName && username && email && password && confirmPass) errorContainer.setAttribute('hidden', true);

    if (username.length < 4) return setError('Username must be at least four characters long.', errorContainer);
    if (password.length < 8) return setError('Password must be at least eight characters long.', errorContainer);

    if (password != confirmPass) return setError('Passwords must match.', errorContainer);
    
    fetch('/soulnotes/users', {
        method: 'POST',
        body: JSON.stringify({
            firstName,
            lastName,
            username,
            email,
            password
        })
    }).then(resp => {
        if (resp.status == 204) {
            let successMsgContainer = document.createElement('p');
            successMsgContainer.setAttribute('class', 'alert alert-success');
            successMsgContainer.innerText = 'Registration successful! Redirecting...';
            document.getElementById('register').appendChild(successMsgContainer);

            // setTimeout(() => {
            //     window.location.href = '/soulnotes';
            // }, 3000);
            return;
        }
        return resp.json();
    }).then(data => {
        console.log(data);
        if (data.code == 409) {
            return setError(data.message, errorContainer);
        }
    }).catch(e => {
        alert(e);
    });
}

function setError(message, o) {
    o.removeAttribute('hidden');
    o.innerText = message;
}