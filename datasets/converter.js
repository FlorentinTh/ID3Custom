var fs = require("fs"),
    args = process.argv.slice(2),
    readline = require('readline'),
    classes = { "normal": ["normal"],
        "attack": ["back", "land", "neptune", "pod", "smurf", "teardrop",
        "ftp_write", "guess_passwd", "imap", "multihop", "phf", "spy", "warezclient", "warezmaster",
        "buffer_overflow", "perl", "loadmodule", "rootkit",
        "ipsweep", "nmap", "portsweep", "satan"]
    };
    /*classes = { "normal": ["normal"],
                "DoS": ["back", "land", "neptune", "pod", "smurf", "teardrop"],
                "R2L": ["ftp_write", "guess_passwd", "imap", "multihop", "phf", "spy", "warezclient", "warezmaster"],
                "U2R": ["buffer_overflow", "perl", "loadmodule", "rootkit"],
                "Probing": ["ipsweep", "nmap", "portsweep", "satan"]
    };*/

if(args.length == 0) {
    throw new Error("Usage: node converter.js [path] [relation_name] [attribute_file_name] [data_file_name]");
}

var path = args[0],
    relation = args[1],
    attribute_path = args[2],
    data_path = args[3],
    header = "% 1. " + relation + "\n" + "@RELATION " + relation + "\n",
    attributes = "",
    rl = null;

fs.readFile(path+"/"+attribute_path, 'utf8',function(err, data){
    if (err) throw err;
    data = data.split(".\n");

    for(var i = 0; i < data.length-1; i++){
        var tmp = data[i].split(": ");
        //attributes += "@ATTRIBUTE " + tmp[0] + " " + tmp[1] + "\n";
        attributes += tmp[0]+",";
    }
    /*var class_tmp = data[data.length-1].split(": ");
    attributes += "@ATTRIBUTE " + class_tmp[0] + " {" + class_tmp[1].substr(0,class_tmp[1].length-1) + "}" + "\n";*/
    attributes += "class"+"\n";

    /*header += attributes + "@DATA\n";
    fs.writeFile(path+"/"+relation+".arff", header, function(err){
        if (err) throw err;
    });*/
    fs.writeFile(path+"/"+relation+".csv", attributes, function(err){
        if (err) throw err;
    });

    rl = readline.createInterface({
        input: fs.createReadStream(path+"/"+data_path)
    });

    rl.on('line', function(input){
        /*fs.appendFile(path+"/"+relation+".arff", input.substr(0, input.length-1)+"\n", function (err) {
            if (err) throw err;
        });*/
        var splitted = input.substr(0, input.length-1).split(",");
        var cont = false;
        for(var key in classes){
            for(var c in classes[key]){
                if(splitted[splitted.length-1] == classes[key][c]) {
                    splitted[splitted.length-1] = key;
                    cont = true;
                    break;
                }
            }
            if(cont) break;
        }

        fs.appendFile(path+"/"+relation+".csv", splitted.join(",")+"\n", function (err) {
            if (err) throw err;
        });
    });
});
