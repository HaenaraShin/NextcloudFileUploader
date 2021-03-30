Nextcloud File Uploader  
=============================

<img src="https://img.shields.io/github/v/release/HaenaraShin/NextcloudFileUploader">

**Kotlin program to upload files to [NextCloud](https://github.com/nextcloud) on cli.**

## Purpose

Upload any files on your own [NextCloud](https://github.com/nextcloud) with this. Without api, it can be a deployment automation server.

## Run

#### arguments :
- -id {User ID} 
- -pwd {User Password} 
- -cloudUrl {NextCloud url} 
- -uploadPath {Destination url} 
- -filePath {Array of local files path to upload split with ','} 
- -driverPath {ChromeDriver path} (optional, default : current directory)
- -timeout {Timeout sec} (optional, default : 15s)

#### example :
```
$ java -jar NextcloudFileUploader.jar -id {ID} -pwd {PASSWORD} -cloudUrl {NextCloud URL} -uploadPath {Upload path} -filePath [fileName1, fileName2, ...] -driverPath {ChromeDriver path} -timeout {Timtout (sec)}
```


## Usage
If your team uses NextCloud, you may use as gradle task to distribute your applications simply as follows.
```
task devAppUpload(type: JavaExec, group: "__upload__") {
  main = "NextCloudUploaderKt"
  classpath = files("$rootDir/nx_uploader/NextCloudUploader.jar")
  args('-id', 'admin',
      '-pwd', 'admin123!',
      '-cloudUrl', 'nxt.ubpay.com/index.php/apps/files?dir=',
      '-uploadPath', '/Android/ubpay/dev',
      '-filePath', "${new FileNameFinder().getFileNames("$buildDir/outputs/apk/", '*.apk')}",
      '-driverPath', "$rootDir/nx_uploader",
      '-timeout', '15'
  )
  doLast {
    println '------------------------ UPLOAD IS DONE -----------------------'
  }
}
```

...and of course, CI/CD is obviously much better way to distribute your app.


## Licence
```
MIT License

Copyright (c) 2019 Haenala Shin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
