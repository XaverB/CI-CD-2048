# Github

1. Create and run a container for hosting the servlet using Tomcat:

```bash
xaver@LAPTOP-PKP1N1EV:/mnt/c/Users/xaver$ docker run -d --name github-runner --network runner-net -p 8081:8080 tomcat:9-jdk17-openjdk-slim
24d07714cf7e990cde99981406cc1a9410f83b8ed4e30a2852b3600f5f5f42ad
```

2. Open a shell in the container:

```bash
Update and install packages:xaver@LAPTOP-PKP1N1EV:/mnt/c/Users/xaver$ docker exec -it github-runner /bin/bash
root@24d07714cf7e:/usr/local/tomcat#
```

3. Update and Update Packages

```bash
apt update
apt upgrade
apt install maven curl
...
done.
Processing triggers for libc-bin (2.31-13+deb11u3) ...
Processing triggers for ca-certificates (20210119) ...
Updating certificates in /etc/ssl/certs...
0 added, 0 removed; done.
Running hooks in /etc/ca-certificates/update.d...

done.
done.
```

4. Change permissions on `/usr/local/tomcat/webapps` to world read-/writeable:

```shell
chmod 777 /usr/local/tomcat/webapps
```

5. Create a new user for running the GitHub self-hosted runner and switch to that user:

```bas
root@24d07714cf7e:/usr/local/tomcat# adduser github-runner
su -l github-runner
Adding user `github-runner' ...
Adding new group `github-runner' (1000) ...
Adding new user `github-runner' (1000) with group `github-runner' ...
Creating home directory `/home/github-runner' ...
Copying files from `/etc/skel' ...
New password:
Retype new password:
passwd: password updated successfully
Changing the user information for github-runner
Enter the new value, or press ENTER for the default
        Full Name []: 2048runner
        Room Number []: 1
        Work Phone []: 1
        Home Phone []: 1
        Other []:
Is the information correct? [Y/n] Y
```

