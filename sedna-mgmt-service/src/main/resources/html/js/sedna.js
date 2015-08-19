function populateStages(){
var queueToStageMap =[];
queueToStageMap["crawl_in"]={name:"crawl",dispatched:0};
queueToStageMap["crawlToParse"]={name:"parse",dispatched:0};
queueToStageMap["parseToFeed"]={name:"feed",dispatched:0};
var url = "http://localhost:9000/hostStatus";
 $.getJSON( url, function( data ) {
   $("#crawl").empty();
   $("#parse").empty();
   $("#feed").empty();
   var stages = data["stageMap"];
   $.each( stages, function( key, val ) {
   var normKey= key.toLowerCase();
   var mem=0;
   $.each( val, function( idx, val ){
    $("#"+normKey).append('<div class="slot '+normKey+'">'+key+'<br/>'
    +'IN:'+val["stageConfig"]["inQueueConfig"]["queueName"]+'<br/>'
    +'OUT:'+val["stageConfig"]["outQueueConfig"]["queueName"]+'<br/>'
    +'</div>');
    str = val["jvmSize"]["maxHeap"];
    mem+= parseInt(str.substring(0, str.length - 1));
    });
    var num = mem/1024;
    var r_num = Math.round(num*100)/100;
    $("#"+normKey+"-stat").empty().append('<span class="smallfont">Committed memory: </span>'+r_num+'GB');
   });
   $.each(data["queueStatsMap"], function(k,v){
   plotGraph(v["queueSize"],"#"+k);

   if(k != "feed_out")
   		queueToStageMap[k].dispatched= v.dispatched;
   });
	 for	(var key in queueToStageMap) {
		$("#"+queueToStageMap[key].name+"-dis").empty()
		.append('<span class="smallfont">Processed: </span>'+queueToStageMap[key].dispatched);
	 }
 });
}

function plotGraph(data, divId){
$(divId).empty();
// define dimensions of graph
		var m = [50, 10, 30, 90]; // margins
		var w = 400 - m[1] - m[3]; // width
		var h = 300 - m[0] - m[2]; // height
		// X scale will fit all values from data[] within pixels 0-w
		var x = d3.scale.linear().domain([0, 60]).range([0, w]);
		// Y scale will fit values from 0-10 within pixels h-0 (Note the inverted domain for the y-scale: bigger is up!)
		var y = d3.scale.linear().domain([0, d3.max(data, function(d, i){return d;})]).range([h, 0]);
			// automatically determining max range can work something like this
			// var y = d3.scale.linear().domain([0, d3.max(data)]).range([h, 0]);
		// create a line function that can convert data[] into x and y points
		var line = d3.svg.line()
			// assign the X function to plot our line as we wish
			.x(function(d,i) {
				// verbose logging to show what's actually being done
				//console.log('Plotting X value for data point: ' + d + ' using index: ' + i + ' to be at: ' + x(i) + ' using our xScale.');
				// return the X coordinate where we want to plot this datapoint
				return x(i);
			})
			.y(function(d) {
				// verbose logging to show what's actually being done
				//console.log('Plotting Y value for data point: ' + d + ' to be at: ' + y(d) + " using our yScale.");
				// return the Y coordinate where we want to plot this datapoint
				return y(d);
			})
			// Add an SVG element with the desired dimensions and margin.
			var graph = d3.select(divId).append("svg:svg")
			      .attr("width", w + m[1] + m[3])
			      .attr("height", h + m[0] + m[2])
			    .append("svg:g")
			      .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

			 graph.append("text")
                           .attr("x", 0)
                           .attr("y", 0)
                           .attr("text-anchor", "middle")
                           .style("font-size", "16px")
                           .style("color","white")
                           .style("text-decoration", "underline")
                           .text(divId.substring(1,divId.length-1)+"Pending Job");


			// create yAxis
			var xAxis = d3.svg.axis().scale(x).tickSize(-h).tickSubdivide(true);
			// Add the x-axis.
			graph.append("svg:g")
			      .attr("class", "x axis")
			      .attr("transform", "translate(0," + h + ")")
			      .call(xAxis);
			// create left yAxis
			var yAxisLeft = d3.svg.axis().scale(y).ticks(4).orient("left");
			// Add the y-axis to the left
			graph.append("svg:g")
			      .attr("class", "y axis")
			      .attr("transform", "translate(-25,0)")
			      .call(yAxisLeft);

  			// Add the line by appending an svg:path element with the data line we created above
			// do this AFTER the axes above so that the line is above the tick-lines
  			graph.append("svg:path").attr("d", line(data));
}
    $( document ).ready(function() {
        populateStages();
        setInterval(function() {
        populateStages();
        }, 2 * 1000);

    });

