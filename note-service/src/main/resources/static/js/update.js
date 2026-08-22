const button = document.getElementById('updateBtn');
const btnText = document.getElementById('updateBtnText');
const btnSpinner = document.getElementById('updateBtnSpinner');
const textDiv = document.getElementById('textDiv');
const result = document.getElementById('result');
const titleCont = document.getElementById('title');
let stage = 'get';

if(button){
    button.addEventListener('click', async (evt) => {
        evt.preventDefault();

        const title = titleCont.value;

        if(!title){
            showToast("Title is empty");
            return;
        }

        localStorage.setItem("title",title);

        button.disabled = true;
        btnSpinner.style.display = 'inline-block';

        if(stage === 'get'){
            await getNote(button, title);
        }else{
            await updateNote(button);
        }
    })
}

async function getNote(button, title) {
    try {
        const response = await fetch("/notes/getNote", {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({title}),
            credentials:'include'
        });

        const data = await response.json();

        if(response.ok){
            btnText.textContent = 'Update Note';
            textDiv.style.display = 'block';
            result.textContent = data.text;
            enableAutoResize(result);
            stage = 'update';
        }else{
            showToast(data.message);
            return;
        }
    } catch (err) {
        console.error(err);
        btnText.textContent = 'Get Note';
        showToast("Something went wrong, Try again later!");
        return;
    } finally{
        button.disabled = false;
        btnSpinner.style.display = 'none';
    }
}

async function updateNote(button){
    try{
        const newTitle = document.getElementById('title').value;
        const newText = document.getElementById('result').value;
        const oldTitle = localStorage.getItem('title');

        const response = await fetch('/notes/update',{
            method:'PUT',
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({oldTitle, newTitle, newText}),
            credentials:'include'
        });
            
        if(response.ok){
            showToast('Note updated successfully','success'); 
            stage = 'get';               
        }else{
            showToast('Note was not updated');
        }
    }catch(err){
        console.error(err);
        showToast("Something went wrong, Try again later!");
        return;
    }finally{
        button.disabled = false;
        btnSpinner.style.display = 'none';
        btnText.textContent = 'Get Note';
        textDiv.style.display = 'none';
    }    
}

enableAutoResize(titleCont, result);