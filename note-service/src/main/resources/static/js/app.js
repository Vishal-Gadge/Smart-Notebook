document.addEventListener('DOMContentLoaded', async (evt) => {
    evt.preventDefault();
    const welcomeMsg = document.getElementById("welcomeMsg");

    try{
        const response = await fetch("/auth/profile");
        const data = await response.json();

        if(response.ok){
            welcomeMsg.textContent = `Welcome ${data.username}`;
        }else{
            showToast("Your profile was not loaded");
        }
    }catch(err){
        showToast("Something went wrong, try again");
        return;
    }
})
























































//const API_BASE = "http://localhost:8080"; // change to your gateway url
//const USER_ID = 1; // for testing. Later get from login
//
//async function apiFetch(endpoint, method, body) {
//  const res = await fetch(API_BASE + endpoint, {
//    method,
//    headers: {
//      "Content-Type": "application/json",
//      "X-User-Id": USER_ID
//    },
//    body: body ? JSON.stringify(body) : null
//  });
//  return res.json();
//}
//
//// Show response in page
//function showResult(data, id="result") {
//  document.getElementById(id).innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
//}