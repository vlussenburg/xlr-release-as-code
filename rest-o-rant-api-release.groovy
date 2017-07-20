// Exported from:        http://4011f63d1886:5516/#/templates/Release00000001/xfile
// XL Release version:   7.0.1
// Date created:         Thu Jul 20 14:32:47 GMT 2017

def server(type, title) {
    def cis = configurationApi.searchByTypeAndTitle(type, title)
    if (cis.isEmpty()) {
        throw new RuntimeException("No CI found for the type '${type}' and title '${title}'")
    }
    if (cis.size() > 1) {
        throw new RuntimeException("More than one CI found for the type '${type}' and title '${title}'")
    }
    cis.get(0)
}
def server1 = server('xldeploy.XLDeployServer','XebiaLabs internal')
xlr {
  release('Release rest-o-rant-api') {
    variables {
      stringVariable('version') {
        
      }
    }
    description 'Release template for demo'
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-11-23T07:30:00+0000')
    phases {
      phase('Preparation Phase') {
        description 'Prepare the whole project'
        scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-11-23T07:30:00+0000')
        dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-11-25T07:30:00+0000')
        tasks {
          gate('Prepare release') {
            description 'Prepare all release plan'
            owner 'John'
            dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-05-03T07:30:00+0000')
            team 'Dev Team'
            conditions {
              condition('Acceptance Testing Done')
              condition('Downtime notification sent')
              condition('Team is updated for release')
            }
          }
          manual('Organize team meeting') {
            description 'Ensures Dev & QA are aligned on the release'
            owner 'John'
            dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-05-03T07:30:00+0000')
            team 'Dev Team'
          }
        }
      }
      phase('Deployment phase') {
        description 'Development phase'
        scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-11-25T07:30:00+0000')
        dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-11-25T22:00:00+0000')
        tasks {
          custom('Deploy to production') {
            description 'Start Deployment on XLD for production'
            owner 'David'
            dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2016-05-06T15:30:00+0000')
            script {
              type 'xldeploy.Deploy'
              server server1
              deploymentPackage 'rest-o-rant-api/${version}'
              deploymentEnvironment 'Prod'
            }
          }
        }
      }
    }
  }
}
