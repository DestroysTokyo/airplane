document.addEventListener('DOMContentLoaded', function () {
  var $burgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
  if($burgers.length > 0) {
    $burgers.forEach(function($burger) {
      $burger.addEventListener('click', function() {
        var $target = document.getElementById($burger.dataset.target);
        $burger.classList.toggle('is-active');
        $target.classList.toggle('is-active');
      });
    });
  }
});