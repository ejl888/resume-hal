module.exports = function (grunt) {

  // Load grunt tasks automatically
  require('load-grunt-tasks')(grunt);

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  var params = {
    app: 'app',
    test: 'test',
    e2etests: 'e2e-tests',
    build: 'build',
    coverage: 'build/coverage',
    dist: 'build/dist',
    tmp: 'build/tmp'
  };

  // Project configuration.
  grunt.initConfig({
    params: params,
    pkg: grunt.file.readJSON('package.json'),

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      bower: {
        files: ['bower.json'],
        tasks: ['wiredep']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= params.app %>/{,*/}{,*/}{,*/}{,*/}!(*.spec|e2e)+(.js)',
          '<%= params.app %>/{,*/}{,*/}{,*/}{,*/}*.html',
          '<%= params.tmp %>/styles/{,*/}*.css',
          '<%= params.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}']
      },
      styles: {
        files: ['<%= params.app %>/styles/{,*/}*.less'],
        tasks: ['less', 'newer:copy:styles', 'autoprefixer'],
        options: {
          livereload: '<%= connect.options.livereload %>'
        }
      }
    },

    // The actual grunt server settings
    connect: {
      options: {
        port: grunt.option('serverPort') || 9000,
        // Change this to '0.0.0.0' to access the server from outside.
        hostname: 'localhost',
        livereload: grunt.option('livereloadPort') || 35729
      },
      proxies: [{
        context: '/resume-hal', // the context of the data service
        host: 'localhost', // wherever the data service is running
        port: 18080 // the port that the data service is running on
      }],
      livereload: {
        options: {
          open: true,
          middleware: function (connect) {
            return [
              require('grunt-connect-proxy/lib/utils').proxyRequest,
              connect.static(params.tmp),
              connect().use('/bower_components', connect.static('./bower_components')),
              connect.static(params.app)];
          }
        }
      },
      test: {
        options: {
          port: 9001,
          middleware: function (connect) {
            return [
              connect.static('build/tmp'),
              connect.static('test'),
              connect().use('/bower_components', connect.static('./bower_components')),
              connect.static(params.app)];
          }
        }
      },
      dist: {
        options: {
          open: true,
          base: '<%= params.dist %>'
        }
      }
    },

    // Make sure code styles are up to par and there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '.jshintrc',
        reporter: require('jshint-stylish')
      },
      prod: {
        src: [
          'Gruntfile.js',
          '<%= params.app %>/**/!(*.spec|*.e2e|*.mock)+(.js)']
      },
      test: {
        options: {
          jshintrc: 'test.jshintrc'
        },
        src: [
          '<%= params.app %>/**/*.spec.js',
          '<%= params.app %>/**/*.mock.js',
          '<%= params.app %>/**/*.e2e.js']
      }
    },

    // Empties folders to start fresh
    clean: {
      build: ['<%= params.build %>'],
      server: '<%= params.tmp %>',
      release: 'build'
    },

    // Add vendor prefixed styles
    autoprefixer: {
      options: {
        browsers: ['last 1 version']
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= params.tmp %>/styles/',
          src: '{,*/}*.css',
          dest: '<%= params.tmp %>/styles/'
        }]
      }
    },

    // Automatically inject Bower components into the app
    wiredep: {
      app: {
        src: ['<%= params.app %>/index.html'],
        ignorePath: /\.\.\//
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        src: [
          '<%= params.dist %>/scripts/{,*/}*.js',
          '<%= params.dist %>/styles/{,*/}*.css',
          '<%= params.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
          '<%= params.dist %>/styles/fonts/*']
      }
    },

    useminPrepare: {
      options: {
        dest: '<%= params.dist %>',
        staging: '<%= params.tmp %>',
        flow: {
          dev: {
            steps: {
              js: ['concat'],
              css: ['cssmin']
            },
            post: {}
          },
          prod: {
            steps: {
              js: ['concat', 'uglifyjs'],
              css: ['cssmin']
            },
            post: {}
          }
        }
      },
      prod: {
        src: '<%= params.app %>/index.html'
      },
      dev: {
        src: '<%= params.app %>/index.html'
      }
    },

    // Performs rewrites based on filerev and the useminPrepare configuration
    usemin: {
      html: ['<%= params.dist %>/{,*/}*.html'],
      css: ['<%= params.dist %>/styles/{,*/}*.css'],
      options: {
        assetsDirs: ['<%= params.dist %>', '<%= params.dist %>/images']
      }
    },

    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= params.app %>/images',
          src: '{,*/}*.{png,jpg,jpeg,gif}',
          dest: '<%= params.dist %>/images'
        }]
      }
    },

    svgmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= params.app %>/images',
          src: '{,*/}*.svg',
          dest: '<%= params.dist %>/images'
        }]
      }
    },

    ngAnnotate: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= params.tmp %>/concat/scripts',
          src: '*.js',
          dest: '<%= params.tmp %>/concat/scripts'
        }]
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= params.app %>',
          dest: '<%= params.dist %>',
          src: [
            '*.html',
            'images/{,*/}*.{webp}',
            'fonts/*']
        }, {
          expand: true,
          cwd: '<%= params.tmp %>/images',
          dest: '<%= params.dist %>/images',
          src: ['generated/*']
        }]
      },
      js: {
        expand: true,
        cwd: '<%= params.tmp %>/concat/scripts/',
        dest: '<%= params.dist %>/scripts/',
        src: '{,*/}*.js'
      },
      templates: {
        expand: true,
        cwd: '<%= params.tmp %>',
        dest: '<%= params.dist %>/scripts/',
        src: '{,*/}templates.js'
      },
      styles: {
        expand: true,
        cwd: '<%= params.app %>/styles',
        dest: '<%= params.tmp %>/styles/',
        src: '{,*/}*.css'
      },
      lcov: {
        expand: false,
        src: '<%= params.coverage %>/*/lcov.info',
        dest: '<%= params.coverage %>/lcov.info'
      }
    },

    concurrent: {
      server: ['copy:styles'],
      test: ['copy:styles'],
      dist: [
        'copy:styles',
        'imagemin',
        'svgmin']
    },

    'code-coverage-enforcer': {
      options: {
        lcovfile: '<%= params.coverage %>/lcov.info',
        lines: 95,
        functions: 95,
        branches: 95,
        src: '<%= params.app %>',
        includes: ['<%= params.app %>/**/*.js'],
        excludes: [
          '<%= params.app %>/**/*.mock.js',
          '<%= params.app %>/**/*.e2e.js',
          '<%= params.app %>/**/*.spec.js']
      }
    },

    // Test settings
    karma: {
      unit: {
        configFile: 'karma.conf.js',
        singleRun: true
      }
    },

    protractor: {
      options: {
        configFile: '<%= params.e2etests %>/protractor.conf.js',
        keepAlive: true
      },
      run: {},
      debug: {
        options: {
          debug: true
        }
      }
    },

    ngtemplates: {
      'resume-ng-client': {
        cwd: '<%= params.app %>',
        src: 'components/{,*/}{,*/}{,*/}*.html',
        dest: '<%= params.tmp %>/templates.js',
        options: {
          htmlmin: {
            collapseBooleanAttributes: true,
            collapseWhitespace: true,
            removeAttributeQuotes: true,
            removeComments: true, // Only if you don't use comment directives!
            removeEmptyAttributes: true,
            removeRedundantAttributes: true,
            removeScriptTypeAttributes: true,
            removeStyleLinkTypeAttributes: true
          }
        }
      }
    },

    compress: {
      main: {
        options: {
          archive: '<%= params.build %>/zip/<%= pkg.name %>-<%= pkg.version %>.zip'
        },
        files: [
          {expand: true, cwd: '<%= params.dist %>', src: ['scripts/*'], ext: '-<%= pkg.version %>.js'},
          {expand: true, cwd: '<%= params.dist %>', src: ['styles/*'], ext: '-<%= pkg.version %>.css'},
          {expand: true, cwd: '<%= params.dist %>', src: ['fonts/*']}
        ]
      }
    },

    less: {
      development: {
        options: {},
        files: {
          'build/tmp/styles/main.css': 'app/styles/main.less'
        }
      }
    },

    bower: {
      install: {
        options: {
          cleanup: false,
          copy: false,
          verbose: true
        }
      }
    },

    htmlangular: {
      files: {
        src: ['<%= params.app %>/**/*.html', '!<%= params.app %>/index.html']
      },
      options: {
        customattrs: [
          'msd-elastic'
        ],
        customtags: [],
        reportpath: 'build/html-angular-validate-report.json',
        tmplext: 'html'
      }
    }
  });

  // Load the plugin that provides the "uglify" task.
  grunt.loadNpmTasks('grunt-contrib-uglify');

  // Default task(s).
  grunt.registerTask('default', ['uglify']);

  grunt.registerTask('validate', ['test', 'jshint', 'force:htmlangular']);

  grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
    if (target === 'dist') {
      return grunt.task.run(['build', 'connect:dist:keepalive']);
    }

    grunt.task.run([
      'clean:server',
      'less',
      'wiredep',
      'concurrent:server',
      'autoprefixer',
      'configureProxies:server',
      'connect:livereload',
      'watch']);
  });

  grunt.registerTask('test', [
    'clean:server',
    'concurrent:test',
    'autoprefixer',
    'connect:test',
    'karma',
    'copy:lcov',
    'code-coverage-enforcer']);

  grunt.registerTask('build', [
    'clean:build',
    'less',
    'wiredep',
    'useminPrepare:prod',
    'ngtemplates',
    'concurrent:dist',
    'autoprefixer',
    'concat',
    'ngAnnotate',
    'copy:dist',
    'copy:templates',
    'cssmin',
    'uglify',
    'filerev',
    'usemin']);

  grunt.registerTask('buildDev', [
    'clean:build',
    'less',
    'wiredep',
    'useminPrepare:dev',
    'ngtemplates',
    'concurrent:dist',
    'autoprefixer',
    'concat',
    'ngAnnotate',
    'copy:dist',
    'copy:templates',
    'copy:js',
    'cssmin',
    'filerev',
    'usemin']);

  grunt.registerTask('default', [
    'newer:jshint',
    'test',
    'build']);


};
