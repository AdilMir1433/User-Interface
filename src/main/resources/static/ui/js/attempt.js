// function HandleForm() {
//     alert("In FUnction")
//     const form = document.getElementById("submitForms");
//     const option1 = document.getElementById("opsion1");
//     const option2 = document.getElementById("opsion2");
//     const option3 = document.getElementById("opsion3");
//     const option4 = document.getElementById("answer");
//
//     alert("About to check")
//     if(option1.checked) {
//         document.getElementById("ans").value = option1.value;
//     }
//     else if (option2.checked) {
//         document.getElementById("ans").value = option2.value;
//     }
//     else if (option3.checked) {
//         document.getElementById("ans").value = option3.value;
//     }
//     else if (option4.checked) {
//         document.getElementById("ans").value = option4.value;
//     }
//     alert("Checked")
//     form.submit();
// }
document.addEventListener("DOMContentLoaded", function() {
    const submitButton = document.getElementById("submit");
    submitButton.addEventListener("click", function() {
        const form = document.getElementById("submitForms");
        const option1 = document.getElementById("opsion1");
        const option2 = document.getElementById("opsion2");
        const option3 = document.getElementById("opsion3");
        const option4 = document.getElementById("opsion4");

        if(option1.checked) {
            document.getElementById("ans").value = option1.value;
        }
        else if (option2.checked) {
            document.getElementById("ans").value = option2.value;
        }
        else if (option3.checked) {
            document.getElementById("ans").value = option3.value;
        }
        else if (option4.checked) {
            document.getElementById("ans").value = option4.value;
        }
        form.submit();
    });
});
