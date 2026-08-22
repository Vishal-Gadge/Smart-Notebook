const button = document.getElementById('addNoteBtn');
const title = document.getElementById('title');
const text = document.getElementById('text');
if(button){
    button.addEventListener('click', async (evt) => {
        evt.preventDefault();

        const title = document.getElementById('title').value;
        const text = document.getElementById('text').value;
        const result = document.getElementById('result');

        if(title.trim() === ""){
            showToast("Title cannot be empty")
            return;
        }

        const btnText = document.getElementById('addNoteBtnText');
        const btnSpinner = document.getElementById('addNoteBtnSpinner');

        button.disabled = true;
        btnText.textContent = "Adding note...";
        btnSpinner.style.display = "inline-block";

        try{
            const response = await fetch('/notes/add',{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify({title, text}),
                credentials:'include'
            });
            
            const data = await response.json();

            if(response.ok){
                showToast(data.message, "success");
            }else{
                showToast(data.message);
            }
        }catch(err){
            console.error(err);
            showToast("Something went wrong, Try again later!");
            return;
        }finally{
            btnText.textContent = "Save Note";
            button.disabled = false;
            btnSpinner.style.display = "none";
        }        
    })
}

enableAutoResize(title, text);