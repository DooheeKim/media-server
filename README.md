# media-server
<h3>ClearKey DRM을 적용한 MPEG-DASH 스트리밍 실습</h3>
<h5>Description</h5>
<br>MPEG-DASH와 DRM을 이용한 동영상 재생의 테스트가 가능합니다.</br>
<br>핵심 기능은 크게 세 가지로 구분할 수 있는데, </br>
<br>1. 영상 업로드 시 DASH로 인코딩하여 화질별로 나누어 저장한 뒤(1080p, 720p, 480p, 360p) shaka packager를 통해 암호화 하는 부분</br>
<br>2. DASH로 인코딩 된 영상을 받아오는 부분</br>
<br>3. 암호화된 영상의 DRM 키를 받아오는 부분</br>
<br>입니다.</br>
<br><b>어디까지나 학습을 위한 코드일뿐, clearKey DRM은 키가 노출되기 때문에 보안상 우수한 DRM이 아닙니다.</b></br>
<h5> Requirements <h5>
<ul>
<li>ffmpeg 설치</li>
<li>shaka-packager 적절한 경로에 두기 (https://github.com/shaka-project/shaka-packager)</li>
<ul>
