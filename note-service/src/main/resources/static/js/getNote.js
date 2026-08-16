const button = document.getElementById('getNoteBtn');
if(button){
    button.addEventListener('click', async (evt) => {
        evt.preventDefault();

        const title = document.getElementById('title').value;

        if(!title){
            showToast('Title cannot be empty');
            return;
        }

        const result = document.getElementById('result');
        const btnText = document.getElementById('getNoteBtnText');
        const btnSpinner = document.getElementById('getNoteBtnSpinner');

        button.disabled = true;
        btnText.textContent = "Getting note...";
        btnSpinner.style.display = "inline-block";

        try {
            const response = await fetch("/notes/getNote",{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify({title}),
                credentials:'include'
            });
            
            const data = await response.json();

            if(response.ok){
                result.style.display = 'inline-block';
                result.textContent = data.text;
            }else{
                showToast(data.message);
            }
        } catch (err) {
            console.error(err);
            showToast("Something went wrong, Try again later!");
            return;
        }finally{
            btnText.textContent = "Get Note";
            button.disabled = false;
            btnSpinner.style.display = "none";
        }
    })
}