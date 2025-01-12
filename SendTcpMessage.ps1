param(
    [string]$message
)

$hostname = "localhost"
$port = 8888

$client = New-Object System.Net.Sockets.TcpClient($hostname, $port)
$stream = $client.GetStream()
$writer = New-Object System.IO.StreamWriter($stream)
$writer.AutoFlush = $true

$writer.WriteLine($message)
$writer.Close()
$stream.Close()
$client.Close()
