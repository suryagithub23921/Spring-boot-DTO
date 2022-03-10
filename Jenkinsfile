#!groovy

pipeline {
    environment {          
			//Docker DTR
			imageName = "idm/oscare-idm-configuration"
			dockerImage = ''
			EMAIL_TO = 'msg.P.GBM.ocx-idp-dev@msg.group'
           }
  agent { node { label 'os-slave' } }
    options {
        timeout(time: 24, unit: 'HOURS')
         }
    stages {
		stage('clean') {
		   steps{
			script{
			       sh 'mvn -X -s settings_ruby.xml clean'
      			}
			}
		}
		stage('test') {
		   steps{
			script{
			       sh 'mvn -X -s settings_ruby.xml test '
      			}
			}
		}
		stage('Install') {
		   steps{
			script{
			        sh 'mvn -X -s settings_ruby.xml install -DskipTests'
                }
			}
		}
		stage('SonarQube analysis') {
            steps {
			  print "SonarQube analysis"
              withSonarQubeEnv('Ruby SonarQube') {
					sh 'mvn -X -s settings_ruby.xml sonar:sonar'
					}
			}
        }
		stage("Building docker image") {
            steps {
			 print "Building docker image"
                script{
					dockerImage = docker.build imageName + ":$BUILD_NUMBER"
                }
            }
        }
		stage('Registry-Push') {
             steps {
                    print 'echo Registry-Push'
                    script {
                            docker.withRegistry('https://docker.rubyhealth.b51.msgruby.cloud', 'nexus-ruby') 
							{
								dockerImage.push()
								dockerImage.push('latest')
                            }
                       }
                  }
        }
		stage('Update helm Chart'){
	         steps{
	              sh'echo update helm Chart'
	              script{
                       sh'sed -i "s/tag: [0-9]*/tag: $BUILD_NUMBER/g" helm-config/values-nightly.yaml'
                       sh'cat helm-config/values-nightly.yaml'
		            }
	        } 
	    }
		stage('Push helm file to bitbucket'){
		      steps{
			        sh'echo Push helm file to bitbucket'
					withCredentials([usernamePassword(credentialsId: 'nexus-user', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh '''
                       echo '#!/bin/sh' > /home/ubuntu/jenkins/git_askpass.sh
                        echo "exec echo \"${GIT_PASSWORD}\"" > /home/ubuntu/jenkins/git_askpass.sh
                        chmod 775 /home/ubuntu/jenkins/git_askpass.sh
                        echo "user: ${GIT_USERNAME}"
                        echo "branch: ${GIT_BRANCH}"
                        GIT_ASKPASS=/home/ubuntu/jenkins/git_askpass.sh
                        export GIT_ASKPASS
                        cat /home/ubuntu/jenkins/git_askpass.sh
                        pwd
                        git status
						git_branch_local=$(echo $GIT_BRANCH   | sed -e "s|origin/||g")
						echo "git_branch_local:" $git_branch_local
                        git config --global user.email "msg.P.GBM.aokcx@msg.group" 
                        git config --global user.name "aokcx"
                        git add . 					
                        git commit -m "jenkins commit"
						git push https://${GIT_USERNAME}@aokcx.gbm.msg.team/vcs-bitbucket/scm/idm/oscare-idm-configuration.git HEAD:$git_branch_local
                    '''
					}
				}		
		}
		stage('CleanUp') {
        steps {
               sh 'echo CleanUp Workspace'
               sh "docker rmi ${imageName}:$BUILD_NUMBER"
			   sh "docker rmi -f docker.rubyhealth.b51.msgruby.cloud/idm/oscare-idm-configuration:$BUILD_NUMBER"
			   
            }
        }
    }
}
