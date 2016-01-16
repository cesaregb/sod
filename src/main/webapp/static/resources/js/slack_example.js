var app = angular.module('slack-example', [ 'ui.bootstrap', 'ngRoute' ]);

app.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider) {
			var urlPartialsBase = '/resources/partials/';
			$routeProvider.when('/home', {
				templateUrl : urlPartialsBase + 'html_parser.html',
				controller : 'htmlParserController'
			}).when('/about', {
				templateUrl : urlPartialsBase + 'about.html',
				controller : 'placeHolderController'
			}).when('/demo_info', {
				templateUrl : urlPartialsBase + 'demo_info.html',
				controller : 'placeHolderController'
			}).otherwise({
				redirectTo : '/home'
			});
			$locationProvider.html5Mode(true);
		} ]
);
/*
 * Controller
 * */

app.controller('placeHolderController', function($scope, $templateCache) {
	// Section doesnt contain logic at the moment...
	$scope.clearCache = function() {
		$templateCache.removeAll();
  };

});

app.controller('htmlParserController', function($scope, httpParserFactory, $compile) {
	var debog = false;
	if (debog) console.log("within the controller");

	$scope.url = "";
	$scope.tagsArray = [];
	$scope.error_message = "";
	$scope.parseError = false;
	$scope.jsoupService = true;

	$scope.getHTML = function(){
		$scope.error_message = "";
		$scope.parseError = false;
		$scope.loading = true;
		$('getButton').prop('disabled', true);
		if($scope.jsoupService){
			httpParserFactory.getHTMLByJsoupURL($scope.url).success(function (result){
				$scope.loading = false;
				$('getButton').prop('disabled', false);
				$scope.tagsArray = [];
				if (result.status == "1"){
					$scope.parseAngularHTML(result.parsedHTML);
					for (var key in result.tagsCount) {
						if (result.tagsCount.hasOwnProperty(key)) {
							$scope.tagsArray.push({key : key, value : key + " " + result.tagsCount[key]});
						}
					}
				}else {
					$scope.error_message = result.errorMessage;
					$scope.parseError = true;
				}
			}).error(function(err){
				$scope.loading = false;
				$('getButton').prop('disabled', false);
				$scope.error_message = "Service error";
				$scope.parseError = true;
			});
		}else{
			httpParserFactory.getHTMLByURL($scope.url).success(function (result){
				$scope.loading = false;
				$('getButton').prop('disabled', false);
				$scope.tagsArray = [];
				if (result.status == "1"){
					$scope.parseAngularHTML(result.parsedHTML);
					for (var key in result.tagsCount) {
						if (result.tagsCount.hasOwnProperty(key)) {
							$scope.tagsArray.push({key : key, value : key + " " + result.tagsCount[key]});
						}
					}
				}else {
					$scope.error_message = result.errorMessage;
					$scope.parseError = true;
				}
			}).error(function(err){
				$scope.loading = false;
				$('getButton').prop('disabled', false);
				$scope.error_message = "Service error";
				$scope.parseError = true;
			});
		}
	};

	var html = "html content will be shown here.... ";
	$scope.parseAngularHTML = function (html){
		//Approach 1, using Angular's directive.
		//Angular normal binding validates the "content" being parsed. This way angular compile the html and parse it, its required
 		$scope.htmlContent = html;
	};
	$scope.parseHTML = function(html){
		// Approach 2, using javascript element, this insert the seleted html, this is not validated by angular html parser.
		document.getElementById("htmlContent").innerHTML = html;
	};

	$scope.selectTag = function(tag){
		/*
			Here we dynamically select the elements, we do it by a pre-defined class assigned to the code.
			This way the UI Engine dont have to iterate thru the elements searching for matches.
		*/
		$(".format_" + tag).parent().parent().scrollTop($(".format_" + tag).offset().top);
		var dynamicStyle = document.getElementById("dynamic-color");
		if (dynamicStyle){
			document.getElementsByTagName("head")[0].removeChild(dynamicStyle);
		}
		if (tag != "none"){
			var style = document.createElement('style');
			style.type = 'text/css';
			style.setAttribute('id', 'dynamic-color');
			style.innerHTML = '.format_' + tag + ' { color: #F00; }';
			document.getElementsByTagName('head')[0].appendChild(style);
		}
	};
	$scope.parseAngularHTML(html);
});

/*
	Angular directive to parse html content in <code>
*/
app.directive('dynamic', function ($compile) {
	return {
		restrict: 'A',
		replace: true,
		link: function (scope, ele, attrs) {
			scope.$watch(attrs.dynamic, function(html) {
				ele.html(html);
				$compile(ele.contents())(scope);
			});
		}
	};
});

/*
 * Factories
 * */
app.factory('httpParserFactory', [ '$http', function($http) {
	var urlBase = '/rest/';
	// var urlBase = 'http://localhost:8080/rest/';
	var myFactory = {};
	myFactory.getHTMLByURL = function (url) {
      return $http.get(urlBase + "url_resolver?url=" + url);
   };
	myFactory.getHTMLByJsoupURL = function (url) {
      return $http.get(urlBase + "url_resolver/jsoup?url=" + url);
   };
	return myFactory;
} ]);
