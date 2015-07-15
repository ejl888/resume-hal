module.exports = function (config) {
  config.set({

    basePath: './',

    files: [
      'bower_components/angular/angular.js',
      'bower_components/angular-route/angular-route.js',
      'bower_components/angular-mocks/angular-mocks.js',
      // App: javascript
      'app/{,*/}{,*/}{,*/}{,*/}!(*.e2e)+(.js)'
    ],

    autoWatch: true,

    colors: true,

    frameworks: ['jasmine'],

    browsers: ['PhantomJS'],

    plugins: [
      'karma-*'
    ],

    ngHtml2JsPreprocessor: {
      stripPrefix: 'app/',
      moduleName: 'directive-templates'
    },

    preprocessors: {
      'app/{,*/}{,*/}{,*/}{,*/}!(*.e2e|*.mock|*.spec)+(.js)': ['coverage']
    },

    logLevel: config.LOG_INFO,

    junitReporter: {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    },

    htmlReporter: {
      outputDir: 'build/karma_html'
    },

    coverageReporter : {
      type : 'lcov',
      dir : 'build/coverage',
      file : 'lcov.info'
    },

    reporters : ['coverage', 'progress', 'html'],

    port: 8081
  });
};
