Pod::Spec.new do |s|
  s.name     = 'android-youtube-query'
  s.version  = '0.1.0'
  s.license  = 'MIT'
  s.summary  = 'android-youtube-query aims to be a drop-in replacement for UITabBarController with the intention of letting developers easily customise its appearance.' 
  s.description = 'android-youtube-query aims to be a drop-in replacement of UITabBarController with the intention of letting developers easily customise its appearance. JBTabBar uses .'
  s.homepage = 'http://jinthagerman.github.com/android-youtube-query'
  s.author   = { 'Jin Budelmann' => 'jin@bitcrank.com' }
  s.source   = { :git => 'https://github.com/jinthagerman/android-youtube-query.git', :tag => '0.1.0' }
  s.platform = :ios
  #s.source_files = 'Pod/Classes/*.{h,m}'
  #s.resources = "Pod/Assets/android-youtube-query.bundle"
  s.requires_arc = true


  # use a builtin version of sqlite3
  s.subspec 'beans' do |ss|
    ss.source_files = 'Pod/Classes/beans/*.{h,m}'
  end

  # use a builtin version of sqlite3
  s.subspec 'callback' do |ss|
    ss.source_files = 'Pod/Classes/callback/*.{h,m}'
  end

  # use a builtin version of sqlite3
  s.subspec 'ui' do |ss|
    ss.source_files = 'Pod/Classes/ui/*.{h,m}'
  end

  # use a builtin version of sqlite3
  s.subspec 'utils' do |ss|
    ss.source_files = 'Pod/Classes/utils/*.{h,m}'
  end

  s.dependency 'AFNetworking'

end
