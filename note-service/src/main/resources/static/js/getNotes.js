document.addEventListener('DOMContentLoaded', async (evt) => {
    evt.preventDefault();

    const result = document.getElementById('result');
    getNotes(result);

    
    const button = document.getElementById('refreshBtn');
    if(button){
        button.addEventListener('click', async (evt) => {
            evt.preventDefault();

            const btnText = document.getElementById('getNoteBtnText');
            const btnSpinner = document.getElementById('getNoteBtnSpinner');

            button.disabled = true;
            btnText.textContent = 'Getting your notes...';
            btnSpinner.style.display = 'inline-block';

            getNotes(result);
        })
    } 
})

async function getNotes(result, button, btnText, btnSpinner) {
    try {
        const response = await fetch("/notes/getNotes");
        const data = await response.json();

        if(response.ok){            
            result.innerHTML = '';
            for(let i=0; i<data.length; i++){
                result.innerHTML += 
                `<div class="note-card">
                    <div class="note-header">${i+1}. ${data[i].title}</div>
                    <textarea readonly>${data[i].text}</textarea> 
                </div>`;
            };
            document.querySelectorAll('textarea').forEach(autoResize);
        }else{
            showToast("No notes exist for you");
            return;
        }
    } catch (err) {
        console.error(err);
        showToast("Something went wrong, Try again later!");
        return;
    } finally {
        button.disabled = false;
        btnText.textContent = 'Refresh';
        btnSpinner.style.display = 'none';
    }
}

function autoResize(textarea){
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}