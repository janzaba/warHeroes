//#############################################################################################################
//############ CLASSES
//#############################################################################################################

function Field (x, y, t){
    this.cords = {
        x : x,
        y : y
    }
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

//#############################################################################################################
//############ GLOBALS
//#############################################################################################################

//#########################################################
//field Counter - this value have to match with value in style.css file
var _fieldCnt = 50;

//#########################################################
//debugMode - change tshi to false in production stage!!
var _debugMode = true;

var _terrainTypes = Array(
        "grass1", "grass2", "grass3", "grass4", "grass5", 
        "rock1", "rock2", "rock3", "rock4", "rock5"
        );
var _terrain = new Terrain();
var _mouseX = 0;
var _mouseY = 0;

//#############################################################################################################
//############ FUNCTIONS
//#############################################################################################################

function init(){
    refreshMouse();                             // start refreshing mouse position
    if( _debugMode )
        $(".debug-mode").show();                // on debug mode on show info box
    buildTerrain();                             // building a random terrain
    setInterval(function(){autoScroll()}, 10);  // starting scrolling map - based on mouse position 
    $( 'body' ).css("overflow", "hidden");      // makeing scrollbars invisible
}

function buildTerrain(){
    for(var i = 0; i < _fieldCnt; i++){
        for(var j = 0; j < _fieldCnt; j++){
            var f = new Field(i, j, randomTerrain());
            _terrain.add(f);
        }
        
    }
}

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
        
        var changeX = x / 5;
        var changeY = y / 10
        if(x > 70){
            $( window ).scrollLeft($( window ).scrollLeft() + changeX);
        }
        if(y > 70){
            $( window ).scrollTop($( window ).scrollTop() + changeY);
        }
        changeX = 15 - changeX;
        changeY = 10 - changeY;
        if(x < 30){
            $( window ).scrollLeft($( window ).scrollLeft() - changeX);
        }
        if(y < 30){
            $( window ).scrollTop($( window ).scrollTop() - changeY);
        }

        //$( "#debug-info" ).append( "<br /> W.width: " + _wWidth + " <br /> W.height: " + _wHeight );
        //$( "#debug-info" ).append( "<br /> W.scrollTop: " + _wScrollTop + " <br /> W.scrollLeft: " + _wScrollLeft );
    
};
//#############################################################################################################
//############ MAIN
//#############################################################################################################

$(document).ready(function(){
    init();                         // start all required starting functions

    // DISPLAING BUILDED TERRAIN
    $('#processing').modal('show');
    for(var i = 0; i < (_fieldCnt * _fieldCnt); i++){
        var field = '<div class="field ' + _terrain.get(i).terrainType + '" id="' + i + '"></div>';
        $('#game').append(field);
    }
    $('#processing').modal('hide');
    
    
    // MAKEING FIELD CLICKABLE
    $('.field').click(function(){
       var id = $(this).attr('id');
       var f = _terrain.get(id);
       var x = f.getX();
       var y = f.getY();
       alert("Clicked on field nr " + id + " [" + x + ", " + y + "]"); 
    });
});

