function closeForm() {
    window.location.href = "/users/home/teacher-dashboard";
}
function setID(id) {
    document.getElementById("examID").value = id.id;
    console.log(id.id);
}