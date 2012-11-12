//Plugin JS
var TTSPlugin = {
		callNativeFunction: function (success, fail, native_action, resultType) {
		return cordova.exec( success, fail, "com.example.phonegapstttest", native_action, [resultType]);
		}
	}; 



document.addEventListener("deviceready",onDeviceReady,false);
var destinationType;

	var inter;
	var stat;
	var res;
	var newPicture= new String();

	
function onDeviceReady() {
    destinationType=navigator.camera.DestinationType;
    Parse.initialize("ZwAvbaMzsLZ1yLntf8hkxzdBw0jp68Ab0PqBTc9c", "wmdZF6zc0ZRB9Vp17lqzFJ85BTThynptig0g4srN");


}
function onBodyLoad() 
	{   
		res=document.getElementById('results');
		res.innerHTML="Waiting for Results";



	}
	function speechButtonPressed(){
		TTSPlugin.callNativeFunction(nativePluginSuccessHandler, nativePluginErrorHandler, "startRecognize", null);
	}


	function nativePluginSuccessHandler(result) {
		//alert("Yay "+result);
		res.innerHTML=result;

	}

	function nativePluginErrorHandler(result) {
		alert("Error "+result);
	}

function capturePhoto() {
	navigator.camera.getPicture(cameraSuccess, nativePluginErrorHandler, { quality: 20, destinationType: destinationType.DATA_URL});
}
function cameraSuccess(imageData){
	var image = document.getElementById('Image');
	newPicture = imageData;
//	newPicture = "data:image/jpeg;base64," + imageData;

	image.src = newPicture;
}
function saveData(){
	var PictureTag = Parse.Object.extend("PictureTag");
	var pictureTag = new PictureTag();
	pictureTag.set("Tag", res.innerHTML);
	pictureTag.set("Picture", newPicture);
	console.log(newPicture);
	pictureTag.save(null, {
		  success: function(pictureTag) {
		    alert("pictureTag Saved");
		  },
		  error: function(pictureTag, error) {
		    alert("Error "+ error);
		  }
		});
	
	
}







