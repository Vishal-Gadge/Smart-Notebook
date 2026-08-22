function showToast(message, type = 'error', duration = 3000){
    const toast = document.getElementById('toast');
    if(!toast) return;
    
    toast.textContent = message;
    toast.className = `show ${type}`;

    setTimeout(() => {
        toast.className = toast.className.replace("show", "");
    }, duration);
}


//utility
function autoResize(e){
    e.target.style.height = 'auto';
    e.target.style.height = e.target.scrollHeight + 'px';
}

function enableAutoResize(...elements) {
    console.log("AutoResize function called");
    elements.forEach(element => {
        if(!element) return;
        element.addEventListener('input', autoResize);
        autoResize({target: element});
    })   
}