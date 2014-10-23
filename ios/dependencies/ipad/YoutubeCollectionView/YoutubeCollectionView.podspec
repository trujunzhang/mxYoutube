Pod::Spec.new do |s|
  s.name     = 'YoutubeCollectionView'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'YoutubeCollectionView aims to be a drop-in replacement for UITabBarController with the intention of letting developers easily customise its appearance.' 
  s.description = 'YoutubeCollectionView aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://jinthagerman.github.com/YoutubeCollectionView'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/jinthagerman/YoutubeCollectionView.git', :tag => '0.1.0' }
  s.platform = :ios
  s.source_files = 'Pod/Classes/*/*.{h,m}'
  s.resources = "Pod/Assets/*.*"
  s.requires_arc = true
  s.dependency 'MLImageCache'
  s.dependency 'KRLCollectionViewGridLayout'
end
