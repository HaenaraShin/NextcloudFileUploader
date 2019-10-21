Nextcloud File Uploader  
=============================

**Kotlin program to upload files to [NextCloud](https://github.com/nextcloud) on cli.**

## Purpose

Upload any files on your own [NextCloud](https://github.com/nextcloud) with this. Without api, it can be a deployment automation server.

## Run

#### arguments :
- -id {User ID} 
- -pwd {User Password} 
- -cloudUrl {NextCloud url} 
- -uploadPath {Destination url} 
- -filePath {Local file path to upload} 
- -driverPath {ChromeDriver path} (optional, default : current directory)
- -timeout {Timeout sec} (optional, default : 15s)

#### example :
```
$ java -jar NextcloudFileUploader.jar -id {ID} -pwd {PASSWORD} -cloudUrl {NextCloud URL} -uploadPath {Upload path} -filePath {Local file path to upload} -driverPath {ChromeDriver path} -timeout {Timtout (sec)}
```


## Usage
If your team uses NextCloud, you may use as gradle task to distribute your application simply.
```
task devAppUpload(type: JavaExec, group: "__upload__") {
  main = "NextCloudUploaderKt"
  classpath = files("$rootDir/nx_uploader/NextCloudUploader.jar")
  args('-id', 'admin'
      '-pwd', 'admin123!'
      '-cloudUrl', 'nxt.ubpay.com/index.php/apps/files?dir='
      '-uploadPath', '/Android/ubpay/dev'
      '-filePath', "$projectDir/build/outputs/apk/" + getApkName(),
      '-driverPath', "$rootDir/nx_uploader"
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
