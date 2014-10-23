Pod::Spec.new do |s|
  s.name     = 'JBTabBarController'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'JBTabBarController aims to be a drop-in replacement for UITabBarController with the intention of letting developers easily customise its appearance.' 
  s.description = 'JBTabBarController aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://jinthagerman.github.com/JBTabBarController'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/jinthagerman/JBTabBarController.git', :tag => '0.1.0' }
  s.platform = :ios
  s.source_files = 'Pod/Classes/*.{h,m}'
  s.resources = "Pod/Assets/JBTabBarController.bundle"
  s.requires_arc = true
  s.dependency 'JRSwizzle'
  end
