(function ($, $document) {
  "use strict";

  // listen for toggle change
  $(document).on("selected", ".alerttemplateselect", function () {
    showHide($(this));
  });

  // show/hide our target depending on toggle state
  function showHide(el) {
      var selectedOption = $(el).find(":selected").text();
      var title = selectedOption.substring(0, selectedOption.indexOf("-"));
      var message = selectedOption.substring(selectedOption.indexOf("-") + 1);
      var titleTarget = $(el).data("update-alert-title");
      var messageTarget = $(el).data("update-alert-message");

       $(titleTarget).val(title);
      $(messageTarget).val(message);

     // $(window).adaptTo("foundation-ui").alert("change", "template changed" + $(titleTarget).val());

  }

})($, $(document));