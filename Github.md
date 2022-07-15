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

## Self-hosted runner

### Download

```bash
# Create a folder
$ mkdir actions-runner && cd actions-runner# Download the latest runner package
$ curl -o actions-runner-linux-x64-2.294.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.294.0/actions-runner-linux-x64-2.294.0.tar.gz# Optional: Validate the hash
$ echo "a19a09f4eda5716e5d48ba86b6b78fc014880c5619b9dba4a059eaf65e131780  actions-runner-linux-x64-2.294.0.tar.gz" | shasum -a 256 -c# Extract the installer
$ tar xzf ./actions-runner-linux-x64-2.294.0.tar.gz
```

```bash
--------------------------------------------------------------------------------
|        ____ _ _   _   _       _          _        _   _                      |
|       / ___(_) |_| | | |_   _| |__      / \   ___| |_(_) ___  _ __  ___      |
|      | |  _| | __| |_| | | | | '_ \    / _ \ / __| __| |/ _ \| '_ \/ __|     |
|      | |_| | | |_|  _  | |_| | |_) |  / ___ \ (__| |_| | (_) | | | \__ \     |
|       \____|_|\__|_| |_|\__,_|_.__/  /_/   \_\___|\__|_|\___/|_| |_|___/     |
|                                                                              |
|                       Self-hosted runner registration                        |
|                                                                              |
--------------------------------------------------------------------------------

# Authentication


√ Connected to GitHub

# Runner Registration

Enter the name of the runner group to add this runner to: [press Enter for Default]

Enter the name of runner: [press Enter for e994dc5d3525]

This runner will have the following labels: 'self-hosted', 'Linux', 'X64'
Enter any additional labels (ex. label-1,label-2): [press Enter to skip]

√ Runner successfully added
√ Runner connection is good

# Runner settings

Enter name of work folder: [press Enter for _work]

√ Settings Saved.

github-runner@e994dc5d3525:~/actions-runner$ ^Cxxxxxxxxxx ./config.sh --url https://github.com/XaverB/CI-CD-2048 --token AE72X5ETQB6FZD2K3WLRUKTC2E2DW--------------------------------------------------------------------------------|        ____ _ _   _   _       _          _        _   _                      ||       / ___(_) |_| | | |_   _| |__      / \   ___| |_(_) ___  _ __  ___      ||      | |  _| | __| |_| | | | | '_ \    / _ \ / __| __| |/ _ \| '_ \/ __|     ||      | |_| | | |_|  _  | |_| | |_) |  / ___ \ (__| |_| | (_) | | | \__ \     ||       \____|_|\__|_| |_|\__,_|_.__/  /_/   \_\___|\__|_|\___/|_| |_|___/     ||                                                                              ||                       Self-hosted runner registration                        ||                                                                              |--------------------------------------------------------------------------------# Authentication√ Connected to GitHub# Runner RegistrationEnter the name of the runner group to add this runner to: [press Enter for Default]Enter the name of runner: [press Enter for e994dc5d3525]This runner will have the following labels: 'self-hosted', 'Linux', 'X64'Enter any additional labels (ex. label-1,label-2): [press Enter to skip]√ Runner successfully added√ Runner connection is good# Runner settingsEnter name of work folder: [press Enter for _work]√ Settings Saved.github-runner@e994dc5d3525:~/actions-runner$ ^C
```



### Configure

```bash
# Create the runner and start the configuration experience
$ ./config.sh --url https://github.com/XaverB/CI-CD-2048 --token AE72X5E47VMANL5WHS4TJS3C2EZPY# Last step, run it!
$ ./run.sh
```

### Using your self-hosted runner

```bash
# Use this YAML in your workflow file for each job
runs-on: self-hosted
```

