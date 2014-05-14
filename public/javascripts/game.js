$(document).ready(function(){
    $('#processing').modal('show');
    for(var i = 0; i < 625; i++){
        var field = '<div class="field" id="' + i + '">' + i + '</div>';
        $('#game').append(field);
    }
    $('#processing').modal('hide');
    
    
    $('.field').click(function(){
       var id = $(this).attr('id');
       alert("Clicked on field nr " + id); 
    });
});