//#############################################################################################################
//############ CLASSES
//#############################################################################################################

function Field (x, y, t){
    this.cords = {
        x : x,
        y : y
    };
    this.terrainType = t;
    this.getTerrain = function () {
        return this.terrainType;
    };
    this.getX = function (){
        return this.cords.x;
    }
    this.getY = function (){
        return this.cords.y;
    }
}
function Terrain (){
    this.fields = Array();
    this.add = function (field){
        this.fields.push(field);
    };
    this.count = function (){
        return this.fields.length;
    };
    this.get = function (index) {
        return this.fields[index];
    };
}
function Player (f, nation){
    this.fieldId = f;
    this.nation = nation;
    this.getNation = function () {
        return this.nation;
    };
    this.getFieldId = function (){
        return this.fieldId;
    };
    this.move = function (field){
        this.fieldId = field;
    };
}

//#############################################################################################################
//############ GLOBALS
//#############################################################################################################

//#########################################################
//field Counter - this value have to match with value in style.css file
var _fieldCnt = 50;

//#########################################################
//debugMode - change this to false in production stage!!
var _debugMode = true;

var _terrainTypes = Array(
        "grass1", "grass2", "grass3", "grass4", "grass5", 
        "rock1", "rock2", "rock3", "rock4", "rock5",
        "rock6", "rock7", "rock8", "rock9", "rock10"
        );
var _nationTypes = Array(
        Array("pl", "usa", "uk"),
        Array("ger", "jap", "it")
    );
var _terrain = new Terrain();
var _players = Array();
var _mouseX = 0;
var _mouseY = 0;
var _applicationBlocked = false;

//#############################################################################################################
//############ FUNCTIONS
//#############################################################################################################

function init(){
    refreshMouse();                             // start refreshing mouse position
    if( _debugMode )
        $(".debug-mode").show();                // on debug mode on show info box
    buildTerrain();                             // building a random terrain
    setInterval(function(){autoScroll()}, 5);  // starting scrolling map - based on mouse position 
    $( 'body' ).css("overflow", "hidden");      // makeing scrollbars invisible

    //creating a players
    //SAMPLE PLAYERS
    var player1 = new Player(204, "pl");
    _players.push(player1);
    var player2 = new Player(1003, "usa");
    _players.push(player2);
    var player3 = new Player(143, "uk");
    _players.push(player3);
    var player4 = new Player(708, "jap");
    _players.push(player4);
    var player5 = new Player(1877, "ger");
    _players.push(player5);
    var player6 = new Player(1503, "it");
    _players.push(player6);


    updateBoard();
};

function buildTerrain(){
    for(var i = 0; i < _fieldCnt; i++){
        for(var j = 0; j < _fieldCnt; j++){
            var f = new Field(i, j, randomTerrain());
            _terrain.add(f);
        }
        
    }
};

function randomTerrain(){
    var countTerrainTypes = _terrainTypes.length;
    var t = Math.floor(Math.random() * countTerrainTypes); 
    return _terrainTypes[t];
};

function refreshMouse(){
    $( window ).on( "mousemove", function( event ){
        _mouseX = event.pageX;
        _mouseY = event.pageY;
    });
}

function autoScroll(){
        var x = _mouseX
        var y = _mouseY;
        var wWidth = $( window ).width();
        var wHeight = $( window ).height();
        var wScrollTop = $( window ).scrollTop();
        var wScrollLeft = $( window ).scrollLeft();

        x = Math.floor((( x - wScrollLeft ) / wWidth ) * 100);
        y = Math.floor((( y - wScrollTop ) / wHeight ) * 100);

        $( "#debug-info" ).html( "M.x: " + x + "% <br /> M.y: " + y + "%");
        
        var changeX = x / 2;
        var changeY = y / 2;
        if(x > 90){
            $( window ).scrollLeft($( window ).scrollLeft() + changeX);
        }
        if(y > 90){
            $( window ).scrollTop($( window ).scrollTop() + changeY);
        }
        changeX = 50 - changeX;
        changeY = 50 - changeY;
        if(x < 10){
            $( window ).scrollLeft($( window ).scrollLeft() - changeX);
        }
        if(y < 10){
            $( window ).scrollTop($( window ).scrollTop() - changeY);
        }

        //$( "#debug-info" ).append( "<br /> W.width: " + _wWidth + " <br /> W.height: " + _wHeight );
        //$( "#debug-info" ).append( "<br /> W.scrollTop: " + _wScrollTop + " <br /> W.scrollLeft: " + _wScrollLeft );
    
};

function updateBoard(){
    $('#processing').modal('show');
    while(waitForUnblock());
    _applicationBlocked = true;
    $("#game").text('');
        for(var i = 0; i < (_fieldCnt * _fieldCnt); i++){
            var field = '<div class="field ' + _terrain.get(i).terrainType + '" id="' + i + '"></div>';
            $('#game').append(field);
        }
        $.each(_players, function(){
            var id = this.getFieldId();
            var nation = this.getNation();
            var player = '<div class="player ' + nation + '"></div>';
            $("#"+id).append(player);
        });
    $('#processing').modal('hide');
    _applicationBlocked = false;
    fixModal();
};


//simply waiting for start
function waitForUnblock(){
    if(_applicationBlocked){
        setTimeout(function(){waitForUnblock();}, 10);
        return true;
    }
    return false;
}
function fixModal(){
    $(".modal-backdrop").hide();
}
//#############################################################################################################
//############ MAIN
//#############################################################################################################

$(document).ready(function(){
    init();                         // start all required starting functions

    _players[5].move(1523);
    updateBoard();
    // MAKEING FIELD CLICKABLE
    $('.field').click(function(){
       var id = $(this).attr('id');
       var f = _terrain.get(id);
       var x = f.getX();
       var y = f.getY();
       alert("Clicked on field nr " + id + " [" + x + ", " + y + "]"); 
    });
});

