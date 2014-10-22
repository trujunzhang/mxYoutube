Pod::Spec.new do |s|
  s.name     = 'MxYoutube-iPad-app'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'MxYoutube-iPad-app aims to be a drop-in replacement for UITabBarController with the intention of letting developers easily customise its appearance.' 
  s.description = 'MxYoutube-iPad-app aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://jinthagerman.github.com/MxYoutube-iPad-app'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/jinthagerman/MxYoutube-iPad-app.git', :tag => '0.1.0' }
  s.platform = :ios
  s.source_files = 'Pod/Classes/*/*.{h,m}'
  s.resources = "Pod/Assets/*/*.*"
  s.requires_arc = true
  s.dependency 'UIColor-HexString'
end
