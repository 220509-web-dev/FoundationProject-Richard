window.onload = function() {
    document.getElementById("login").addEventListener("click", login);
}
function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/soulnotes/auth', {
        method: 'POST',
        body: JSON.stringify({username, password})
    }).then(() => {
        alert('Signed in successfully!');
    }).catch(() => {
        alert('oops');
    });
}