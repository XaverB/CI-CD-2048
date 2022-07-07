# 2048 Web Application

## GitLab

### Execute GitLab runner locally in a WSL 2 shell:

1. Start WSL 2 shell, get root privileges, update and install packages:
   ```shell
   wsl -d Debian
   sudo -i
   apt update
   apt upgrade
   apt install nano curl git
   ```

2. Create a Docker network for all containers:
   ```shell
   docker network create runner-net
   ```

3. Download GitLab runner:
   ```shell
   cd /root
   # for Linux on Intel-based CPUs:
   curl -o gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64
   # for macOS on Intel-based CPUs:
   curl -o gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-darwin-amd64
   # for macOS on ARM-based CPUs:
   curl -o gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-darwin-arm64
   chmod 755 gitlab-runner
   ```

4. Register runner with ```docker``` executor:
   * default image for docker executor: ```maven:3.8.5-openjdk-17```
   ```shell
   /root/gitlab-runner register
   ```

5. Register runner with ```shell``` executor:
   ```shell
   /root/gitlab-runner register
   ```

6. Tweak runner config:
   ```shell
   nano /etc/gitlab-runner/config.toml
   ```
   * add ```network_mode = "runner-net"``` to runner config, i.e.:
   ```yaml
   [[runners]]
     ...
     executor = "docker"
     ...
     [runners.docker]
       ...
       network_mode = "runner-net"   # run containers in custom network
   ```

7. Start the runner:
   * press ```Ctrl+C``` to terminate the runner
   ```shell
   /root/gitlab-runner run
   ```

8. Remove everything, if the runner is not needed anymore:
   ```shell
   rm -rf /root/gitlab-runner
   rm -rf /root/builds
   rm -rf /etc/gitlab-runner
   docker network rm runner-net
   ```

----------------------------------------------------

## GitHub

### Setup local CI/CD environment for deployment job in GitHub Actions:

1. Create and run a container for hosting the servlet using Tomcat:
   ```shell
   docker run -d --name github-runner --network runner-net -p 8081:8080 tomcat:9-jdk17-openjdk-slim
   ```

2. Open a shell in the container:
   ```shell
   docker exec -it github-runner /bin/bash
   ```

3. Update and install packages:
   ```shell
   apt update
   apt upgrade
   apt install maven curl
   ```

4. Change permissions on ```/usr/local/tomcat/webapps``` to world read-/writeable:
   ```shell
   chmod 777 /usr/local/tomcat/webapps
   ```

5. Create a new user for running the GitHub self-hosted runner and switch to that user:
   ```shell
   adduser github-runner
   su -l github-runner
   ```

6. Install and run self-hosted runner for Linux X64 as explained in the settings of your repository (Settings &rarr; Actions &rarr; Runners)

7. To stop the runner, press ```Ctrl+C```.

8. If the runner is not needed anymore, ```exit``` the container shell and then stop and remove the container:
   ```shell
   docker stop github-runner
   docker rm github-runner
   ```

----------------------------------------------------

## SonarQube

### Integrate SonarQube for static code quality analysis:

1. Create and run a SonarQube container:
   ```shell
   # for Intel-based CPUs:
   docker run -d --name sonarqube --restart always \
              --network runner-net -p 9000:9000 \
              -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
              sonarqube:8.9.8-community
   
   # for ARM-based CPUs:
   docker run -d --name sonarqube --restart always \
              --network runner-net -p 9000:9000 \
              -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
              mwizner/sonarqube:8.9.5-community
   ```

2. Log in to SonarQube (initial credentials: admin/admin), create a new project, and adapt CI/CD config accordingly
