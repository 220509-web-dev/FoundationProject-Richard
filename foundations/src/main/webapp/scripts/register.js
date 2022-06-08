window.onload = (() => {
    document.getElementById("register").addEventListener("click", register);
});

function register() {
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let username = document.getElementById("username").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let confirmPass = document.getElementById("confirmPassword").value;

    if (!firstName) return alert('First name is a required field.');
    if (!lastName) return alert('Last name is a required field.');
    if (!username) return alert('Username is a required field.');
    if (!email) return alert('Email is a required field.');
    if (!password || !confirmPass) return alert('Password is a required field.');

    console.log(username.length);
    if (username.length < 4) return alert('Username must be at least four characters long.');
    if (password.length < 8) return alert('Password must be at least eight characters long.');

    if (password != confirmPass) return alert('Passwords must match.');
    
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
            alert(`Registered successfully! Welcome, ${username}!`);
            window.location.href = '/soulnotes/'; //todo: move this to user dashboard
        } else {
            alert('An error occurred while making the user.');
        }
    }).catch(e => {
        alert(e);
    });
}