window.onload = function() {
    document.getElementById("login").addEventListener("click", login);
}
function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/soulnotes/auth', {
        method: 'POST',
        body: JSON.stringify({username, password})
    }).then(resp => {
        if (resp.status == 200) {
            alert(`Signed in! Welcome, ${username}!`);
        } else {
            alert('Invalid credentials provided. Please try again.');
        }
    });
};