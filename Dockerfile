FROM centos:6.6

RUN yum -y install wget tar unzip openssh-clients yum-plugin-ovl
RUN rpm -ivh ftp://fr2.rpmfind.net/linux/dag/redhat/el6/en/x86_64/dag/RPMS/axel-2.4-1.el6.rf.x86_64.rpm
RUN rpm -ivh ftp://fr2.rpmfind.net/linux/dag/redhat/el6/en/x86_64/dag/RPMS/sshpass-1.05-1.el6.rf.x86_64.rpm

#-----------------------------------------------------------------
RUN axel -a -n 10 -o /tmp/jdk-8-linux-x64.rpm "http://sdlcbrm.hiw.com/artifactory/ext-release-local/jdk/jdk-8u112-linux-x64.rpm"

RUN yum -y install /tmp/jdk-8-linux-x64.rpm

RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

ENV JAVA_HOME /usr/java/latest
#-----------------------------------------------------------------

ENV FUSE_VERSION 6.2.1.redhat_084-85
#-------
RUN axel -a -n 50 -o /tmp/redhat-jboss-fuse-$FUSE_VERSION.noarch.rpm "http://sdlcbrm.hiw.com/artifactory/yum-local/thirdparty/current/noarch/redhat-jboss-fuse-$FUSE_VERSION.noarch.rpm"
RUN yum -y install /tmp/redhat-jboss-fuse-$FUSE_VERSION.noarch.rpm

RUN wget http://download.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
RUN rpm -ihv epel-release-6-8.noarch.rpm
RUN yum -y install htop mc

RUN echo 'export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=if-enabled -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 "' > /etc/sysconfig/redhat-jboss-fuse

RUN echo 'log4j.logger.com.ihg=DEBUG' >> /opt/fuse/redhat-jboss-fuse/etc/org.ops4j.pax.logging.cfg

RUN echo 'alias client="./opt/fuse/redhat-jboss-fuse/bin/client"' >> ~/.bashrc
RUN echo 'alias tlog="tail -f /var/log/fuse/redhat-jboss-fuse/redhat-jboss-fuse.log"' >> ~/.bashrc

VOLUME /opt/fuse/redhat-jboss-fuse/deploy

CMD service redhat-jboss-fuse start && bash