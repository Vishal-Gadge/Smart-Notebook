document.addEventListener('DOMContentLoaded', async (evt) => {
    evt.preventDefault();

    const result = document.getElementById('result');
    try{
        getNotes(result);
    }catch(err){
        console.error(err);
        showToast("Something went wrong, Try again later!");
        return;
    }
    
    const button = document.getElementById('refreshBtn');
    const btnText = document.getElementById('refreshBtnText');
    const btnSpinner = document.getElementById('refreshBtnSpinner');
    
    if(button){
        button.addEventListener('click', async (evt) => {
            evt.preventDefault();
            button.disabled = true;
            btnText.textContent = 'Getting your notes...';
            btnSpinner.style.display = 'inline-block';

            try{
                getNotes(result);
            }catch (err) {
                console.error(err);
                showToast("Something went wrong, Try again later!");
                return;
            } finally {
                button.disabled = false;
                btnText.textContent = 'Refresh';
                btnSpinner.style.display = 'none';
            }           
        })
    } 
})

async function getNotes(result) {
        const response = await fetch("/notes/getNotes");
        const data = await response.json();

        if(response.ok && data.length !== 0){            
            result.innerHTML = '';
            for(let i=0; i<data.length; i++){
                result.innerHTML += 
                `<div class="note-card">
                    <div class="note-header">
                        <span class="note-number">${i+1}.</span> 
                        <textarea class="note-title" readonly rows="1">${data[i].title}</textarea>
                    </div>
                    <textarea class="note-content" readonly rows="1">${data[i].text}</textarea> 
                </div>`;
            };
            document.querySelectorAll('textarea').forEach(element => {
                enableAutoResize(element);
            });
        }else{
            showToast("No notes exist for you");
            return;
        }
}