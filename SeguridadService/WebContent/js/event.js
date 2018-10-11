var canvas = document.createElement("canvas");
canvas.width = 500;
canvas.height = 500;
document.getElementById("game").append(canvas);

var context = canvas.getContext("2d");
this.spaceship = {
    color: "green",
    width: 20,
    height: 45,
    position: {
        x: 20,
        y: 20
    },
    velocity: {
        x: 0,
        y: 0
    },
    angle: Math.PI / 2,
}

function drawSpaceship() {
    context.save();
    context.beginPath();
    context.translate(spaceship.position.x, spaceship.position.y);
    context.rotate(spaceship.angle);
    context.rect(spaceship.width * -0.5, spaceship.height * -0.5, spaceship.width, spaceship.height);
    context.fillStyle = spaceship.color;
    context.fill();
    context.closePath()
    context.restore();
}

function draw() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    drawSpaceship();
    requestAnimationFrame(draw);
}

draw();

var es = new EventSource("../Soap");
es.onmessage = function (event) {
	console.log(event.data);
	switch (event.data) {
	default:
		spaceship.position = { 
				x: spaceship.position.x,
				y: spaceship.position.y
		};
	break;
	case ("up"):
		spaceship.position = { 
				x: spaceship.position.x,
				y: spaceship.position.y - 35
		};
	break;
	case ("down"):
		spaceship.position = { 
				x: spaceship.position.x,
				y: spaceship.position.y + 35
		};
	break;
	case ("right"):
		spaceship.position = { 
				x: spaceship.position.x + 35,
				y: spaceship.position.y
		};
	break;
	case ("left"):
		spaceship.position = { 
				x: spaceship.position.x - 35,
				y: spaceship.position.y
		};
	break;
	} 
}