
var btnLike = document.querySelector(".item-btn")
if (btnLike) {
    btnLike.addEventListener("click", () => {
        btnLike.classList.toggle("like")
    })
}

var btnTheme = document.querySelector(".theme-btn")
var themes = document.querySelectorAll(".theme")

themes.forEach(elem => {
    btnTheme.addEventListener("click", () => {
        elem.classList.toggle("dark")
    })
})