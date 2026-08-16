function showToast(message, type = 'error', duration = 5000){
    const toast = document.getElementById('toast');
    if(!toast) return;
    
    toast.textContent = message;
    toast.className = `show ${type}`;

    setTimeout(() => {
        toast.className = toast.className.replace("show", "");
    }, duration);
}