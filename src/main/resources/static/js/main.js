
var btnLike = document.querySelector(".item-btn")
if (btnLike) {
    btnLike.addEventListener("click", () => {
        btnLike.classList.toggle("like")
    })
}
