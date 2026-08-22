const button = document.getElementById('deleteBtn');
const btnText = document.getElementById('deleteBtnText');
const btnSpinner = document.getElementById('deleteBtnSpinner');
const showResult = document.getElementById('result');
const textLabel = document.getElementById('textLabel');
const titleCont = document.getElementById('title');
let stage = 'check';

if(button){
    button.addEventListener('click', async (evt) => {
        const title = titleCont.value;
        if(!title){
            showToast("Title cannot be empty");
            return;
        }

        button.disabled = true;
        btnSpinner.style.display = 'inline-block';

        if(stage == 'check'){
            checkDeleteNote(title, btnText);
        }else{            
            DeleteNote(title, btnText);
        }

        button.disabled = false;
        btnSpinner.style.display = 'none';
    })
}

async function checkDeleteNote(title, btnText){
    btnText.textContent = 'Getting ready...';
    
    try {
        const response = await fetch('/notes/getNote', {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({title}),
            credentials:'include'
        });

        const data = await response.json();

        if(response.ok){
            textLabel.style.display = 'inline-block';
            showResult.textContent = data.text; 
            showResult.style.display = 'inline-block';
            enableAutoResize(showResult);
            btnText.textContent = 'Confirm Delete';
            stage = 'delete';
        }else{
            showToast("Note doesn't exist with that Title");
            btnText.textContent = 'Delete';
            return;
        }
    } catch (err) {
        console.error(err);
        showToast("Something went wrong, Try again later!");
        btnText.textContent = 'Delete';
        return;
    }
}

async function DeleteNote(title, btnText) {
    btnText.textContent = 'Deleting...';

    try{
        const response = await fetch('/notes/delete', {
            method:"DELETE",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({title}),
            credentials:'include'
        });

        if(response.ok){
            showToast("Note deleted successfully", "success");
        }else{
            showToast("Something went wrong, Try again later!");
            return;
        }
    }catch(err){
        console.error(err);
        showToast("Something went wrong, Try again later!");
        return;
    }finally{
        btnText.textContent = 'Delete';
        textLabel.style.display = 'none';
        showResult.style.display = 'none';
        stage = 'check';        
    }
}

enableAutoResize(titleCont, showResult);