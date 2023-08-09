function submitForm() {
    const form = document.getElementById("formSubmit");
    form.submit();
}

function submitForms() {
    const form = document.getElementById("formSubmits");
    form.submit();
}
function openForm() {
    document.getElementById("subjectForm").style.display = "block";
}

function closeForms() {
    document.getElementById("subjectForm").style.display = "none";
}

let examID = 0;
function confirmApprove(id) {
    document.getElementById("approveForm").style.display = "block";
    examID = id.id;
}

function submitApproveForm()  {
    document.getElementById("applyForApproval").value = examID;
    const form = document.getElementById("abbrove");
    form.submit();
}

function confirmUnApprove(id) {
    document.getElementById("unapproveForm").style.display = "block";
    examID = id.id;
}

function submitUnApproveForm(){
    document.getElementById("applyForUnApproval").value = examID;
    const form = document.getElementById("unabbrove");
    form.submit();
}
function viewStudents(){
    document.getElementById("student-form").submit();
}