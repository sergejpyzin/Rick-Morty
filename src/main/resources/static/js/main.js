const btnLike = document.querySelectorAll(".item-btn");

btnLike.forEach(elem => {

  elem.addEventListener("click", () => {
    elem.classList.toggle("like");
  });
});


var btnTheme = document.querySelector(".theme-btn");
var theme = document.querySelectorAll(".theme")
theme.forEach(elem => {
    btnTheme.addEventListener("click", () => {
        elem.classList.toggle("dark")
    })
})
// btnTheme.forEach(elem)